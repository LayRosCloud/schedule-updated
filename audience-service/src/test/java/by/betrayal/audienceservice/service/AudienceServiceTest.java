package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.core.mapper.AudienceMapperImpl;
import by.betrayal.audienceservice.core.utils.equals.AudienceEquals;
import by.betrayal.audienceservice.core.utils.generator.AudienceGenerator;
import by.betrayal.audienceservice.core.utils.generator.AudienceTypeGenerator;
import by.betrayal.audienceservice.core.utils.generator.CorpusGenerator;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.exception.NotFoundException;
import by.betrayal.audienceservice.mapper.AudienceMapper;
import by.betrayal.audienceservice.repository.AudienceRepository;
import by.betrayal.audienceservice.repository.AudienceTypeRepository;
import by.betrayal.audienceservice.repository.CorpusRepository;
import by.betrayal.audienceservice.service.impl.AudienceServiceImpl;
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
public class AudienceServiceTest {
    @Mock
    private AudienceRepository audienceRepository;

    @Mock
    private AudienceTypeRepository audienceTypeRepository;

    @Mock
    private CorpusRepository corpusRepository;

    @Spy
    private AudienceMapper mapper = new AudienceMapperImpl();

    @InjectMocks
    private AudienceServiceImpl audienceService;

    @Test
    void findAll_happyPath() {
        //given
        var expected = AudienceGenerator.generateAudiences(5);
        var corpus = CorpusGenerator.generateCorpusWithId();

        when(corpusRepository.findById(corpus.getId())).thenReturn(Optional.of(corpus));
        when(audienceRepository.findAllByCorpus(corpus)).thenReturn(expected);

        //when
        var actual = audienceService.findAll(corpus.getId());

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllPageable_happyPath() {
        //given
        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(0, 5, sort);

        var expected = AudienceGenerator.generateAudiences(5);
        var corpus = CorpusGenerator.generateCorpusWithId();

        when(corpusRepository.findById(corpus.getId())).thenReturn(Optional.of(corpus));
        when(audienceRepository.findAllByCorpus(corpus, pageable)).thenReturn(new PageImpl<>(expected));

        //when
        var options = new PageableOptions(5, 0);
        var actual = audienceService.findAll(corpus.getId(), options);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.items().size());
        Assertions.assertEquals(expected.size(), actual.totalCount());
    }

    @Test
    void findById_happyPath() {
        //given
        var expected = AudienceGenerator.generateAudienceWithId();

        when(audienceRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = audienceService.findById(expected.getId());

        //then
        AudienceEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void findById_throwNotFoundPath() {
        //given
        when(audienceRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundException.class, ()-> audienceService.findById(1L));

        //then
    }

    @Test
    void create_happyPath() {
        //given
        var corpus = CorpusGenerator.generateCorpusWithId();
        var type = AudienceTypeGenerator.generateTypeWithId();
        var expected = AudienceGenerator.generateCreateAudience(corpus.getId(), type.getId());
        when(audienceTypeRepository.findById(type.getId())).thenReturn(Optional.of(type));
        when(corpusRepository.findById(corpus.getId())).thenReturn(Optional.of(corpus));
        when(audienceRepository.save(Mockito.any(AudienceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        //when
        var actual = audienceService.create(expected);

        //then
        AudienceEquals.equalsDto(actual, expected);
    }

    @Test
    void update_happyPath() {
        //given
        var corpus = CorpusGenerator.generateCorpusWithId();
        var type = AudienceTypeGenerator.generateTypeWithId();
        var audience = AudienceGenerator.generateAudienceWithId();
        var expected = AudienceGenerator.generateUpdateAudience(audience.getId(), corpus.getId(), type.getId());
        when(audienceTypeRepository.findById(type.getId())).thenReturn(Optional.of(type));
        when(corpusRepository.findById(corpus.getId())).thenReturn(Optional.of(corpus));
        when(audienceRepository.save(Mockito.any(AudienceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(audienceRepository.findById(audience.getId())).thenReturn(Optional.of(audience));
        //when
        var actual = audienceService.update(expected);

        //then
        AudienceEquals.equalsDto(actual, expected);
    }

    @Test
    void delete_happyPath() {
        //given
        var expected = AudienceGenerator.generateAudienceWithId();

        when(audienceRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = audienceService.delete(expected.getId());

        //then
        AudienceEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() {
        //given
        when(audienceRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundException.class, ()-> audienceService.delete(1L));

        //then
    }
}
