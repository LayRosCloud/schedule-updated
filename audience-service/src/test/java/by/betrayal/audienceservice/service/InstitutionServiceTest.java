package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.core.utils.generator.InstitutionGenerator;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import by.betrayal.audienceservice.exception.NotFoundException;
import by.betrayal.audienceservice.mapper.InstitutionMapper;
import by.betrayal.audienceservice.repository.InstitutionRepository;
import by.betrayal.audienceservice.service.impl.InstitutionServiceImpl;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
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

import static by.betrayal.audienceservice.core.utils.equals.InstitutionEquals.*;
import static by.betrayal.audienceservice.core.utils.generator.InstitutionGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstitutionServiceTest {

    @Mock
    private InstitutionRepository repository;

    @Spy
    private InstitutionMapper mapper = Mappers.getMapper(InstitutionMapper.class);

    @InjectMocks
    private InstitutionServiceImpl service;

    @Test
    void findAll_happyPath() {
        //given
        var expected = generateInstitutionList(5);
        when(repository.findAll()).thenReturn(expected);

        //when
        var actual = service.findAll();

        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllPageable_happyPath() {
        //given
        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(0, 5, sort);
        var expected = generateInstitutionList(5);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(expected));

        //when
        var options = new PageableOptions(5, 0);
        var actual = service.findAll(options);

        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.items().size());
        assertEquals(expected.size(), actual.totalCount());
    }

    @Test
    void findById_happyPath() {
        //given
        var expected = generateInstitutionWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.findById(expected.getId());

        //then
        equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, () -> service.findById(1L));

        //then
    }

    @Test
    void create_happyPath() {
        //given
        var expected = generateCreateInstitution();
        when(repository.save(Mockito.any(InstitutionEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = service.create(expected);

        //then
        equalsDto(actual, expected);
    }

    @Test
    void update_happyPath() {
        //given
        var item = generateInstitutionWithId();
        var expected = generateUpdateInstitution(item.getId());
        when(repository.findById(item.getId())).thenReturn(Optional.of(item));
        when(repository.save(Mockito.any(InstitutionEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = service.update(expected);

        //then
        equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() {
        //given
        var expected = generateInstitutionWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.delete(expected.getId());

        //then
        equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, ()-> service.delete(1L));

        //then
    }
}
