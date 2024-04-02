package by.betrayal.personalservice.controller;

import by.betrayal.personalservice.core.IntegrationTest;
import by.betrayal.personalservice.core.database.PersonDao;
import by.betrayal.personalservice.core.utils.equals.PersonEquals;
import by.betrayal.personalservice.core.utils.generator.PersonGenerator;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PersonControllerTest extends IntegrationTest {

    private final PersonDao dao;

    @Autowired
    public PersonControllerTest(PersonDao dao, MockMvc mockMvc, ObjectMapper mapper) {
        super(mockMvc, mapper);
        this.dao = dao;
    }

    @BeforeEach
    void setUp() {
        dao.clearTable();
    }

    @Test
    void findAll_happyPath() throws Exception {
        //given
        var expected = dao.saveArray(5);

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/people"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getMapper().readValue(bytes, new TypeReference<List<PersonFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllPagination_happyPath() throws Exception {
        //given
        dao.saveArray(10);
        var _limit = 5;
        var _page = 1;

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get(String.format("/v1/people?_limit=%d&_page=%d", _limit, _page)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var bytes = result.getResponse().getContentAsByteArray();
        var totalCountString = result.getResponse().getHeader("x-total-count");
        var actual = getMapper().readValue(bytes, new TypeReference<List<PersonFullDto>>() {
        });

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(totalCountString);
        Assertions.assertEquals(5, actual.size());
        Assertions.assertEquals(10, Long.parseLong(totalCountString));
    }

    @Test
    void findById_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders.get("/v1/people/" + expected.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getMapper().readValue(bytes, PersonFullDto.class);

        //then
        Assertions.assertNotNull(actual);
        PersonEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders.get("/v1/people/" + 999L))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //then
    }

    @Test
    void create_happyPath() throws Exception {
        //given
        var createDto = PersonGenerator.generateCreateDto();
        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders
                        .multipart("/v1/people")
                        .file("photo", createDto.getPhoto().getBytes())
                        .param("lastName", createDto.getLastName())
                        .param("firstName", createDto.getFirstName())
                        .param("patronymic", createDto.getPatronymic())
                        .param("institutionId", createDto.getInstitutionId().toString())
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getMapper().readValue(bytes, PersonFullDto.class);

        //then
        PersonEquals.equalsDto(actual, createDto);
    }

    @Test
    void create_badRequestPath() throws Exception {
        //given
        var createDto = PersonGenerator.generateCreateDto();
        createDto.setLastName("");
        //when
        getMockMvc().perform(MockMvcRequestBuilders
                        .multipart("/v1/people")
                        .file("photo", createDto.getPhoto().getBytes())
                        .param("lastName", createDto.getLastName())
                        .param("firstName", createDto.getFirstName())
                        .param("patronymic", createDto.getPatronymic())
                        .param("institutionId", createDto.getInstitutionId().toString())
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        //then
    }

    @Test
    void update_happyPath() throws Exception {
        //given
        var item = dao.save();
        var updateDto = PersonGenerator.generateUpdateDto(item.getId());

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders
                        .put("/v1/people")
                        .param("id", updateDto.getId().toString())
                        .param("lastName", updateDto.getLastName())
                        .param("firstName", updateDto.getFirstName())
                        .param("patronymic", updateDto.getPatronymic())
                        .param("institutionId", updateDto.getInstitutionId().toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getMapper().readValue(bytes, PersonFullDto.class);
        //then
        PersonEquals.equalsDto(actual, updateDto);
    }

    @Test
    void update_throwBadRequestPath() throws Exception {
        //given
        var updateDto = PersonGenerator.generateUpdateDto(21321L);
        updateDto.setFirstName("");
        //when
        getMockMvc().perform(MockMvcRequestBuilders
                        .put("/v1/people")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .param("id", updateDto.getId().toString())
                        .param("lastName", updateDto.getLastName())
                        .param("firstName", updateDto.getFirstName())
                        .param("patronymic", updateDto.getPatronymic())
                        .param("institutionId", updateDto.getInstitutionId().toString())
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void delete_happyPath() throws Exception {
        //given
        var expected = dao.save();

        //when
        var result = getMockMvc().perform(MockMvcRequestBuilders
                        .delete("/v1/people/" + expected.getId())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var bytes = result.getResponse().getContentAsByteArray();
        var actual = getMapper().readValue(bytes, PersonFullDto.class);
        //then
        PersonEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() throws Exception {
        //given

        //when
        getMockMvc().perform(MockMvcRequestBuilders
                        .delete("/v1/people/" + 999L)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        //then
    }
}
