package by.betrayal.personalservice.service;

import by.betrayal.personalservice.core.utils.equals.PersonEquals;
import by.betrayal.personalservice.entity.PersonEntity;
import by.betrayal.personalservice.exception.BadRequestException;
import by.betrayal.personalservice.exception.NotFoundException;
import by.betrayal.personalservice.mapper.PersonMapper;
import by.betrayal.personalservice.repository.PersonRepository;
import by.betrayal.personalservice.service.impl.PersonServiceImpl;
import by.betrayal.personalservice.utils.pageable.PageOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static by.betrayal.personalservice.core.utils.generator.PersonGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository repository;

    @Spy
    private PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void findAll_happyPath() {
        //given
        var expected = generatePersons(5);
        when(repository.findAll()).thenReturn(expected);

        //when
        var actual = personService.findAll();

        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllPageable_happyPath() {
        //given
        var expected = generatePersons(5);
        var sort = Sort.by("lastName").ascending();
        var pageable = PageRequest.of(0, 5, sort);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<PersonEntity>(expected.subList(0, 5)));
        //when
        var actual = personService.findAll(new PageOptions(5, 0));

        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.items().size());
        assertEquals(expected.size(), actual.totalCount());
    }

    @Test
    void findById_happyPath() {
        //given
        var expected = generatePersonWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));
        //when
        var actual = personService.findById(expected.getId());

        //then
        PersonEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() {
        //given
        final var id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, () -> personService.findById(id));

        //then
    }

    @Test
    void create_happyPath() {
        //given
        var dto = generateCreateDto();
        when(repository.save(Mockito.any(PersonEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = personService.create(dto);

        //then
        PersonEquals.equalsDto(actual, dto);
    }

    @Test
    void update_happyPath() {
        //given
        var expected = generatePersonWithId();
        var dto = generateUpdateDto(expected.getId());
        when(repository.save(Mockito.any(PersonEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = personService.update(dto);

        //then
        PersonEquals.equalsDto(actual, dto);
    }

    @Test
    void delete_happyPath() {
        //given
        var expected = generatePersonWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = personService.delete(expected.getId());

        //then
        PersonEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, () -> personService.delete(1L));

        //then
    }
}
