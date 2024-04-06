package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.core.IntegrationTest;
import by.betrayal.audienceservice.core.database.AudienceDao;
import by.betrayal.audienceservice.core.database.EventDao;
import by.betrayal.audienceservice.core.utils.equals.EventEquals;
import by.betrayal.audienceservice.core.utils.generator.EventGenerator;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public class EventControllerTest extends IntegrationTest {
    private final EventDao dao;
    private final AudienceDao audienceDao;

    @Autowired
    public EventControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, EventDao dao, AudienceDao audienceDao) {
        super(mockMvc, objectMapper);
        this.dao = dao;
        this.audienceDao = audienceDao;
    }

    @BeforeEach
    void setUp() {
        dao.clearTable();
    }

    @Test
    void findAll_happyPath() throws Exception {
        //given
        var expected = dao.save(5);
        var audienceId = expected.get(0).getAudience().getId();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/" + audienceId + "/events"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<EventFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(5, actual.size());
    }

    @Test
    void findAll_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/" + 1 + "/events"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }

    @Test
    void findAllPageable_happyPath() throws Exception {
        //given
        var expected = dao.save(10);
        var audienceId = expected.get(0).getAudience().getId();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/"+ audienceId +"/events?_limit=5&_page=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var totalCountString = result.getResponse().getHeader("x-total-count");
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<EventFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(totalCountString);
        Assertions.assertEquals(5, actual.size());
        Assertions.assertEquals(expected.size(), Long.valueOf(totalCountString));
    }

    @Test
    void findById_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/events/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, EventFullDto.class);

        //then
        EventEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/events/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void create_happyPath() throws Exception {
        //given
        var audience = audienceDao.save();
        var expected = EventGenerator.generateCreateEvent(audience.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.post("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, EventFullDto.class);

        //then
        EventEquals.equalsDto(actual, expected);
    }

    @Test
    void create_throwNotFoundPath() throws Exception {
        //given
        var expected = EventGenerator.generateCreateEvent(1L);

        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void create_throwBadRequestPath() throws Exception {
        //given
        var expected = EventGenerator.generateCreateEvent(1L);
        expected.setValue("");
        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
    }

    @Test
    void update_throwBadRequestPath() throws Exception {
        //given
        var expected = EventGenerator.generateUpdateEvent(1L, 1L);
        expected.setValue("");
        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
    }

    @Test
    void update_happyPath() throws Exception {
        //given
        var updateObject = dao.save();
        var audience = audienceDao.save();
        var expected = EventGenerator.generateUpdateEvent(updateObject.getId(), audience.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.put("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, EventFullDto.class);

        //then
        EventEquals.equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/events/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, EventFullDto.class);

        //then
        EventEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/events/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }
}
