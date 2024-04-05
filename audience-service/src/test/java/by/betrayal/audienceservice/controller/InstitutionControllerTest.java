package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.core.IntegrationTest;
import by.betrayal.audienceservice.core.database.InstitutionDao;
import by.betrayal.audienceservice.core.utils.equals.InstitutionEquals;
import by.betrayal.audienceservice.core.utils.generator.InstitutionGenerator;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
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

public class InstitutionControllerTest extends IntegrationTest {

    private final InstitutionDao dao;

    @Autowired
    public InstitutionControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, InstitutionDao dao) {
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
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/institutions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<InstitutionFullDto>>() {
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
        var result = getMockMvc().perform(MockMvcRequestBuilders.get(String.format("/v1/institutions?_limit=%d&_page=%d", 5, 1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, new TypeReference<List<InstitutionFullDto>>() {
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
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/institutions/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, InstitutionFullDto.class);

        //then
        InstitutionEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/institutions/" + 999L))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }

    @Test
    void create_happyPath() throws Exception {
        //given
        var expected = InstitutionGenerator.generateCreateInstitution();

        //when
        var result = getMockMvc().perform(
                        MockMvcRequestBuilders.post("/v1/institutions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, InstitutionFullDto.class);

        //then
        InstitutionEquals.equalsDto(actual, expected);
    }

    @Test
    void create_throwBadRequestPath() throws Exception {
        //given
        var expected = InstitutionGenerator.generateCreateInstitution();
        expected.setName("");

        //when
        getMockMvc().perform(
                        MockMvcRequestBuilders.post("/v1/institutions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
    }

    @Test
    void update_throwBadRequestPath() throws Exception {
        //given
        var item = dao.save();
        var expected = InstitutionGenerator.generateUpdateInstitution(item.getId());
        expected.setName("");

        //when
        getMockMvc().perform(
                        MockMvcRequestBuilders.post("/v1/institutions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
    }

    @Test
    void update_throwNotFoundPath() throws Exception {
        //given
        var expected = InstitutionGenerator.generateUpdateInstitution(1L);
        expected.setName("");

        //when
        getMockMvc().perform(
                        MockMvcRequestBuilders.post("/v1/institutions")
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
        var expected = InstitutionGenerator.generateUpdateInstitution(item.getId());

        //when
        var result = getMockMvc().perform(
                        MockMvcRequestBuilders.put("/v1/institutions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, InstitutionFullDto.class);

        //then
        InstitutionEquals.equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(
                        MockMvcRequestBuilders.delete("/v1/institutions/" + expected.getId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getObjectMapper().writeValueAsString(expected))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getObjectMapper().readValue(bytes, InstitutionFullDto.class);

        //then
        InstitutionEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.delete("/v1/institutions/" + 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //then
    }
}
