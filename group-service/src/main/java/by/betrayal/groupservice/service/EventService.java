package by.betrayal.groupservice.service;

import by.betrayal.groupservice.dto.event.CreateEventDto;
import by.betrayal.groupservice.dto.event.EventFullDto;
import by.betrayal.groupservice.dto.event.UpdateEventDto;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableOptions;

public interface EventService {

    PageableContainer<EventFullDto> findAllByGroup(Long groupId, PageableOptions options);
    EventFullDto findById(Long id);
    EventFullDto create(CreateEventDto dto);
    EventFullDto update(UpdateEventDto dto);
    EventFullDto delete(Long id);
}
