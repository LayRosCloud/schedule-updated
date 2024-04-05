package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.core.IntegrationTest;
import by.betrayal.audienceservice.core.database.AudienceDao;
import by.betrayal.audienceservice.core.database.AudienceTypeDao;
import by.betrayal.audienceservice.core.database.CorpusDao;
import by.betrayal.audienceservice.core.utils.equals.AudienceEquals;
import by.betrayal.audienceservice.core.utils.generator.AudienceGenerator;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
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

public class AudienceControllerTest extends IntegrationTest {

    private final AudienceDao dao;
    private final AudienceTypeDao typeDao;
    private final CorpusDao corpusDao;

    @Autowired
    public AudienceControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, AudienceDao dao, AudienceTypeDao typeDao, CorpusDao corpusDao) {
        super(mockMvc, objectMapper);
        this.dao = dao;
        this.typeDao = typeDao;
        this.corpusDao = corpusDao;
    }

    @BeforeEach
    void setUp() {
        dao.clearTable();
    }

    @Test
    void findAll_happyPath() throws Exception {
        //given
        var expected = dao.save(5);
        var corpusId = expected.get(0).getCorpus().getId();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/housings/" + corpusId + "/audiences"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<AudienceFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(5, actual.size());
    }

    @Test
    void findAll_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/housings/" + 1 + "/audiences"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }

    @Test
    void findAllPageable_happyPath() throws Exception {
        //given
        var expected = dao.save(10);
        var corpusId = expected.get(0).getCorpus().getId();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/housings/"+ corpusId +"/audiences?_limit=5&_page=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var totalCountString = result.getResponse().getHeader("x-total-count");
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<AudienceFullDto>>() {
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
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceFullDto.class);

        //then
        AudienceEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/audiences/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void create_happyPath() throws Exception {
        //given
        var corpus = corpusDao.save();
        var type = typeDao.save();
        var expected = AudienceGenerator.generateCreateAudience(corpus.getId(), type.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.post("/v1/audiences")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceFullDto.class);

        //then
        AudienceEquals.equalsDto(actual, expected);
    }

    @Test
    void create_throwNotFoundPath() throws Exception {
        //given
        var type = typeDao.save();
        var expected = AudienceGenerator.generateCreateAudience(1L, type.getId());

        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/audiences")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void create_throwBadRequestPath() throws Exception {
        //given
        var type = typeDao.save();
        var expected = AudienceGenerator.generateCreateAudience(1L, type.getId());
        expected.setName("");
        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/audiences")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
    }

    @Test
    void update_throwBadRequestPath() throws Exception {
        //given
        var type = typeDao.save();
        var expected = AudienceGenerator.generateCreateAudience(1L, type.getId());
        expected.setName("");
        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/audiences")
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
        var corpus = corpusDao.save();
        var type = typeDao.save();
        var expected = AudienceGenerator.generateUpdateAudience(updateObject.getId(), corpus.getId(), type.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.put("/v1/audiences")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceFullDto.class);

        //then
        AudienceEquals.equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/audiences/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, AudienceFullDto.class);

        //then
        AudienceEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/audiences/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }
}
