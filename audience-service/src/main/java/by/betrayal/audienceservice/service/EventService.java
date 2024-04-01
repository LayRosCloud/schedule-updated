package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import by.betrayal.audienceservice.dto.event.EventUpdateDto;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;

import java.util.List;

public interface EventService {

    TotalPageable<EventFullDto> findAll(Long audienceId, PageableOptions options);
    List<EventFullDto> findAll(Long audienceId);
    EventFullDto findById(Long id);
    EventFullDto create(EventCreateDto dto);
    EventFullDto update(EventUpdateDto dto);
    EventFullDto delete(Long id);
}
