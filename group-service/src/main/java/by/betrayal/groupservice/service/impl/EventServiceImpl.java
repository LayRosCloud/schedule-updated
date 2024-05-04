package by.betrayal.groupservice.service.impl;

import by.betrayal.groupservice.dto.event.CreateEventDto;
import by.betrayal.groupservice.dto.event.EventFullDto;
import by.betrayal.groupservice.dto.event.UpdateEventDto;
import by.betrayal.groupservice.entity.EventEntity;
import by.betrayal.groupservice.entity.GroupEntity;
import by.betrayal.groupservice.mapper.EventMapper;
import by.betrayal.groupservice.repository.EventRepository;
import by.betrayal.groupservice.repository.GroupRepository;
import by.betrayal.groupservice.service.EventService;
import by.betrayal.groupservice.utils.DateUtils;
import by.betrayal.groupservice.utils.ExceptionUtils;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableFactory;
import by.betrayal.groupservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;
    private final EventMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<EventFullDto> findAllByGroup(Long groupId, PageableOptions options) {
        var group = findByIdGroupOrThrowNotFoundException(groupId);
        var pageable = PageableFactory.getPageableAsc(options);
        var eventPage = eventRepository.findAllByGroup(group, pageable);
        var events = mapper.mapToDto(eventPage.getContent());
        return new PageableContainer<>(eventPage.getTotalElements(), events);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findById(Long id) {
        EventEntity event = findByIdEventOrThrowNotFoundException(id);
        return mapper.mapToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto create(CreateEventDto dto) {
        var group = findByIdGroupOrThrowNotFoundException(dto.getGroupId());
        var event = mapper.mapToEntity(dto);
        event.setDateStart(DateUtils.getTicks(dto.getDateStart()));
        event.setDateEnd(DateUtils.getTicks(dto.getDateEnd()));
        event.setGroup(group);
        var result = eventRepository.save(event);
        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public EventFullDto update(UpdateEventDto dto) {
        var group = findByIdGroupOrThrowNotFoundException(dto.getGroupId());
        var event = findByIdEventOrThrowNotFoundException(dto.getId());
        mapper.mapToEntity(event, dto);
        event.setGroup(group);
        var result = eventRepository.save(event);
        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public EventFullDto delete(Long id) {
        var event = findByIdEventOrThrowNotFoundException(id);
        eventRepository.delete(event);
        return mapper.mapToDto(event);
    }

    private GroupEntity findByIdGroupOrThrowNotFoundException(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Group", groupId)
        );
    }

    private EventEntity findByIdEventOrThrowNotFoundException(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Event", id)
        );
    }
}
