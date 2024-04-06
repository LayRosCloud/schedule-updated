package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import by.betrayal.audienceservice.exception.NotFoundException;
import by.betrayal.audienceservice.mapper.AudienceTypeMapper;
import by.betrayal.audienceservice.repository.AudienceTypeRepository;
import by.betrayal.audienceservice.service.impl.AudienceTypeServiceImpl;
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

import static by.betrayal.audienceservice.core.utils.equals.AudienceTypeEquals.*;
import static by.betrayal.audienceservice.core.utils.generator.AudienceTypeGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AudienceTypeServiceTest {
    @Mock
    private AudienceTypeRepository repository;

    @Spy
    private AudienceTypeMapper mapper = Mappers.getMapper(AudienceTypeMapper.class);

    @InjectMocks
    private AudienceTypeServiceImpl service;

    @Test
    void findAll_happyPath() {
        //given
        var expected = generateTypes(5);
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
        var expected = generateTypes(5);
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
        var expected = generateTypeWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.findById(expected.getId());

        //then
        equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() {
        //given
        when(repository.findById((short)1)).thenReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, () -> service.findById((short)1));

        //then
    }

    @Test
    void create_happyPath() {
        //given
        var expected = generateCreateType();
        when(repository.save(Mockito.any(AudienceTypeEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = service.create(expected);

        //then
        equalsDto(actual, expected);
    }

    @Test
    void update_happyPath() {
        //given
        var item = generateTypeWithId();
        var expected = generateUpdateType(item.getId());
        when(repository.findById(item.getId())).thenReturn(Optional.of(item));
        when(repository.save(Mockito.any(AudienceTypeEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = service.update(expected);

        //then
        equalsEntityAndDto(item, actual);
    }

    @Test
    void delete_happyPath() {
        //given
        var expected = generateTypeWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.delete(expected.getId());

        //then
        equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() {
        //given
        when(repository.findById((short) 1)).thenReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, ()-> service.delete((short) 1));

        //then
    }
}
