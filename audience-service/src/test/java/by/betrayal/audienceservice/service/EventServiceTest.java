package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.core.mapper.EventMapperImpl;
import by.betrayal.audienceservice.core.utils.equals.EventEquals;
import by.betrayal.audienceservice.core.utils.generator.AudienceGenerator;
import by.betrayal.audienceservice.core.utils.generator.EventGenerator;
import by.betrayal.audienceservice.entity.EventEntity;
import by.betrayal.audienceservice.exception.NotFoundException;
import by.betrayal.audienceservice.mapper.EventMapper;
import by.betrayal.audienceservice.repository.AudienceRepository;
import by.betrayal.audienceservice.repository.EventRepository;
import by.betrayal.audienceservice.service.impl.EventServiceImpl;
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
public class EventServiceTest {
    @Mock
    private EventRepository repository;

    @Mock
    private AudienceRepository audienceRepository;

    @Spy
    private EventMapper mapper = new EventMapperImpl();

    @InjectMocks
    private EventServiceImpl service;

    @Test
    void findAll_happyPath() {
        //given
        var audience = AudienceGenerator.generateAudienceWithId();
        when(audienceRepository.findById(audience.getId())).thenReturn(Optional.of(audience));
        var expected = EventGenerator.generateEventList(5);
        when(repository.findAllByAudience(audience)).thenReturn(expected);

        //when
        var actual = service.findAll(audience.getId());

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllPageable_happyPath() {
        //given
        var audience = AudienceGenerator.generateAudienceWithId();

        var sort = Sort.by("value").ascending();
        var pageable = PageRequest.of(0, 5, sort);
        var expected = EventGenerator.generateEventList(5);

        when(audienceRepository.findById(audience.getId())).thenReturn(Optional.of(audience));
        when(repository.findAllByAudience(audience, pageable)).thenReturn(new PageImpl<>(expected));

        //when
        var options = new PageableOptions(5, 0);
        var actual = service.findAll(audience.getId(), options);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.size(), actual.items().size());
        Assertions.assertEquals(expected.size(), actual.totalCount());
    }

    @Test
    void findById_happyPath() {
        //given
        var expected = EventGenerator.generateEventWithId();
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var result = service.findById(expected.getId());

        //then
        EventEquals.equalsEntityAndDto(expected, result);
    }

    @Test
    void findById_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundException.class, ()->service.findById(1L));

        //then
    }

    @Test
    void create_happyPath() {
        //given
        var audience = AudienceGenerator.generateAudienceWithId();
        var expected = EventGenerator.generateCreateEvent(audience.getId());
        when(audienceRepository.findById(audience.getId())).thenReturn(Optional.of(audience));
        when(repository.save(Mockito.any(EventEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        //when
        var actual = service.create(expected);

        //then
        EventEquals.equalsDto(actual, expected);
    }

    @Test
    void update_happyPath() {
        //given
        var audience = AudienceGenerator.generateAudienceWithId();
        var expected = EventGenerator.generateEventWithId();
        var dto = EventGenerator.generateUpdateEvent(expected.getId(), audience.getId());

        when(audienceRepository.findById(audience.getId())).thenReturn(Optional.of(audience));
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        when(repository.save(Mockito.any(EventEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        //when
        var actual = service.update(dto);

        //then
        EventEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_happyPath() {
        //given
        var expected = EventGenerator.generateEventWithId();

        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        //when
        var actual = service.delete(expected.getId());

        //then
        EventEquals.equalsEntityAndDto(expected, actual);
    }

    @Test
    void delete_throwNotFoundPath() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(NotFoundException.class, () -> service.delete(1L));

        //then
    }
}
