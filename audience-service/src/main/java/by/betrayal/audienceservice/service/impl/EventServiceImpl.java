package by.betrayal.audienceservice.service.impl;

import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import by.betrayal.audienceservice.dto.event.EventUpdateDto;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.entity.EventEntity;
import by.betrayal.audienceservice.mapper.EventMapper;
import by.betrayal.audienceservice.repository.AudienceRepository;
import by.betrayal.audienceservice.repository.EventRepository;
import by.betrayal.audienceservice.service.EventService;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final AudienceRepository audienceRepository;
    private final EventMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public TotalPageable<EventFullDto> findAll(Long audienceId, PageableOptions options) {
        var audience = findByIdAudienceOrThrowNotFoundException(audienceId);
        var sort = Sort.by("value").ascending();
        var pageable = PageRequest.of(options.page(), options.limit(), sort);
        var eventPage = eventRepository.findAllByAudience(audience, pageable);
        var events = mapper.mapToFullDto(eventPage.toList());

        return new TotalPageable<>(events, eventPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> findAll(Long audienceId) {
        var audience = findByIdAudienceOrThrowNotFoundException(audienceId);
        var events = eventRepository.findAllByAudience(audience);
        return mapper.mapToFullDto(events);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findById(Long id) {
        var event = findByIdEventOrThrowNotFoundException(id);
        return mapper.mapToFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto create(EventCreateDto dto) {
        var audience = findByIdAudienceOrThrowNotFoundException(dto.getAudienceId());
        var event = mapper.mapToEntity(dto);
        event.setAudience(audience);

        var result = eventRepository.save(event);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public EventFullDto update(EventUpdateDto dto) {
        var event = findByIdEventOrThrowNotFoundException(dto.getId());
        var audience = findByIdAudienceOrThrowNotFoundException(dto.getAudienceId());

        mapper.mapToEntity(event, dto);
        event.setAudience(audience);

        var result = eventRepository.save(event);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public EventFullDto delete(Long id) {
        var event = findByIdEventOrThrowNotFoundException(id);

        eventRepository.delete(event);

        return mapper.mapToFullDto(event);
    }

    private EventEntity findByIdEventOrThrowNotFoundException(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Event with id %d is not found", id)
        );
    }

    private AudienceEntity findByIdAudienceOrThrowNotFoundException(Long id) {
        return audienceRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Audience with id %d is not found", id)
        );
    }
}
