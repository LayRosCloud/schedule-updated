package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.core.IntegrationTest;
import by.betrayal.audienceservice.core.database.CorpusDao;
import by.betrayal.audienceservice.core.database.InstitutionDao;
import by.betrayal.audienceservice.core.utils.equals.CorpusEquals;
import by.betrayal.audienceservice.core.utils.generator.CorpusGenerator;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
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

public class CorpusControllerTest extends IntegrationTest {
    private final CorpusDao dao;
    private final InstitutionDao institutionDao;

    @Autowired
    public CorpusControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, CorpusDao dao, InstitutionDao institutionDao) {
        super(mockMvc, objectMapper);
        this.dao = dao;
        this.institutionDao = institutionDao;
    }

    @BeforeEach
    void setUp() {
        dao.clearTable();
    }

    @Test
    void findAll_happyPath() throws Exception {
        //given
        var expected = dao.save(5);
        var institutionId = expected.get(0).getInstitution().getId();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/institutions/" + institutionId + "/housings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<CorpusFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(5, actual.size());
    }

    @Test
    void findAll_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/institutions/" + 1 + "/housings"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }

    @Test
    void findAllPageable_happyPath() throws Exception {
        //given
        var expected = dao.save(10);
        var corpusId = expected.get(0).getInstitution().getId();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/institutions/"+ corpusId +"/housings?_limit=5&_page=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var totalCountString = result.getResponse().getHeader("x-total-count");
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<CorpusFullDto>>() {
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
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/housings/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, CorpusFullDto.class);

        //then
        CorpusEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/housings/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void create_happyPath() throws Exception {
        //given
        var institution = institutionDao.save();
        var expected = CorpusGenerator.generateCreateCorpus(institution.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.post("/v1/housings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, CorpusFullDto.class);

        //then
        CorpusEquals.equalsDto(actual, expected);
    }

    @Test
    void create_throwNotFoundPath() throws Exception {
        //given
        var expected = CorpusGenerator.generateCreateCorpus(1L);

        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/housings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }

    @Test
    void create_throwBadRequestPath() throws Exception {
        //given
        var expected = CorpusGenerator.generateCreateCorpus(1L);
        expected.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/housings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
    }

    @Test
    void update_throwBadRequestPath() throws Exception {
        //given
        var expected = CorpusGenerator.generateUpdateCorpus(1L, 1L);
        expected.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        //when
        getMockMvc().perform(MockMvcRequestBuilders.post("/v1/housings")
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
        var institution = institutionDao.save();
        var expected = CorpusGenerator.generateUpdateCorpus(updateObject.getId(), institution.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.put("/v1/housings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, CorpusFullDto.class);

        //then
        CorpusEquals.equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/housings/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, CorpusFullDto.class);

        //then
        CorpusEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/housings/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }
}
