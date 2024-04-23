package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.TimeFullDto;
import by.betrayal.scheduleservice.dto.time.UpdateTimeDto;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;

import java.util.List;

public interface TimeService {

    PageableContainer<TimeFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options);
    List<TimeFullDto> findAllByInstitutionId(Long institutionId);
    TimeFullDto findById(Long id);
    TimeFullDto create(CreateTimeDto dto);
    TimeFullDto update(UpdateTimeDto dto);
    TimeFullDto delete(Long id);
}
