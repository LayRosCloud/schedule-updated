package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.core.mapper.CorpusMapperImpl;
import by.betrayal.audienceservice.core.utils.equals.CorpusEquals;
import by.betrayal.audienceservice.core.utils.generator.CorpusGenerator;
import by.betrayal.audienceservice.core.utils.generator.InstitutionGenerator;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.exception.NotFoundException;
import by.betrayal.audienceservice.mapper.CorpusMapper;
import by.betrayal.audienceservice.repository.CorpusRepository;
import by.betrayal.audienceservice.repository.InstitutionRepository;
import by.betrayal.audienceservice.service.impl.CorpusServiceImpl;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CorpusServiceTest {

    @Mock
    private CorpusRepository repository;

    @Mock
    private InstitutionRepository institutionRepository;

    @Spy
    private CorpusMapper mapper = new CorpusMapperImpl();

    @InjectMocks
    private CorpusServiceImpl service;

    @Test
    void findAll_happyPath() {
        //given
        var expected = CorpusGenerator.generateCorpusList(5);
        var institution = InstitutionGenerator.generateInstitutionWithId();

        when(repository.findAllByInstitution(institution)).thenReturn(expected);
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));

        //when
        var actual = service.findAll(institution.getId());

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllPageable_happyPath() {
        //given
        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(0, 5, sort);

        var expected = CorpusGenerator.generateCorpusList(5);
        var institution = InstitutionGenerator.generateInstitutionWithId();

        when(repository.findAllByInstitution(institution, pageable)).thenReturn(new PageImpl<>(expected));
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));

        //when
        var options = new PageableOptions(5, 0);
        var actual = service.findAll(institution.getId(), options);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.items().size());
        Assertions.assertEquals(expected.size(), actual.totalCount());
    }

    @Test
    void findById_happyPath() {
        //given
        var expected = CorpusGenerator.generateCorpusWithId();

        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.findById(expected.getId());

        //then
        CorpusEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundException.class, ()-> service.findById(1L));

        //then
    }

    @Test
    void create_happyPath() {
        //given
        var institution = InstitutionGenerator.generateInstitutionWithId();
        var dto = CorpusGenerator.generateCreateCorpus(institution.getId());
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));
        when(repository.save(Mockito.any(CorpusEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = service.create(dto);

        //then
        CorpusEquals.equalsDto(actual, dto);
    }

    @Test
    void update_happyPath() {
        //given
        var institution = InstitutionGenerator.generateInstitutionWithId();
        var corpus = CorpusGenerator.generateCorpusWithId();
        var dto = CorpusGenerator.generateUpdateCorpus(corpus.getId(), institution.getId());

        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));
        when(repository.findById(corpus.getId())).thenReturn(Optional.of(corpus));
        when(repository.save(Mockito.any(CorpusEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        var actual = service.update(dto);

        //then
        CorpusEquals.equalsDto(actual, dto);
    }

    @Test
    void delete_happyPath() {
        //given
        var expected = CorpusGenerator.generateCorpusWithId();

        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.delete(expected.getId());

        //then
        CorpusEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundException.class, ()-> service.delete(1L));

        //then
    }
}
