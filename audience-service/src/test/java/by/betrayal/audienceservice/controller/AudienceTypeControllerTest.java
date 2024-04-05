package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.core.IntegrationTest;
import by.betrayal.audienceservice.core.database.AudienceTypeDao;
import by.betrayal.audienceservice.core.utils.equals.AudienceTypeEquals;
import by.betrayal.audienceservice.core.utils.generator.AudienceTypeGenerator;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
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

public class AudienceTypeControllerTest extends IntegrationTest {

    private final AudienceTypeDao dao;

    @Autowired
    public AudienceTypeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, AudienceTypeDao dao) {
        super(mockMvc, objectMapper);
        this.dao = dao;
    }

    @BeforeEach
    void setUp() {
        dao.clearTable();
    }

    @Test
    void findAll_happyPath() throws Exception {
        //given
        var expected = dao.save(5);

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/types"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<AudienceTypeFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAll_throwNotFoundPath() throws Exception {
        //given
        var expected = dao.save(10);


        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get(String.format("/v1/audiences/types?_limit=%d&_page=%d", 5, 1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<AudienceTypeFullDto>>() {
        });

        var totalCountString = result.getResponse().getHeader("x-total-count");

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
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/types/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceTypeFullDto.class);

        //then
        AudienceTypeEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/types/" + 999L))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }

    @Test
    void create_happyPath() throws Exception {
        //given
        var expected = AudienceTypeGenerator.generateCreateType();

        //when
        var result = getMockMvc().perform(
                                MockMvcRequestBuilders.post("/v1/audiences/types")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceTypeFullDto.class);

        //then
        AudienceTypeEquals.equalsDto(actual, expected);
    }

    @Test
    void create_throwBadRequestPath() throws Exception {
        //given
        var expected = AudienceTypeGenerator.generateCreateType();
        expected.setName("");

        //when
        getMockMvc().perform(
                        MockMvcRequestBuilders.post("/v1/audiences/types")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        //then
    }

    @Test
    void update_throwBadRequestPath() throws Exception {
        //given
        var dto = dao.save();
        var expected = AudienceTypeGenerator.generateUpdateType(dto.getId());
        expected.setName("");

        //when
        getMockMvc().perform(
                        MockMvcRequestBuilders.post("/v1/audiences/types")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        //then
    }

    @Test
    void update_throwNotFoundPath() throws Exception {
        //given
        var expected = AudienceTypeGenerator.generateUpdateType((short) 1);

        //when
        getMockMvc().perform(
                        MockMvcRequestBuilders.put("/v1/audiences/types")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void update_happyPath() throws Exception {
        //given
        var item = dao.save();
        var expected = AudienceTypeGenerator.generateUpdateType(item.getId());

        //when
        var result = getMockMvc().perform(
                        MockMvcRequestBuilders.put("/v1/audiences/types")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceTypeFullDto.class);

        //then
        AudienceTypeEquals.equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(
                        MockMvcRequestBuilders.delete("/v1/audiences/types/" + expected.getId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceTypeFullDto.class);

        //then
        AudienceTypeEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/audiences/types/" + 1L))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }
}
