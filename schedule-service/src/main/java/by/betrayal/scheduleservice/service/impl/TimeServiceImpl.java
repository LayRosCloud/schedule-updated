package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.TimeFullDto;
import by.betrayal.scheduleservice.dto.time.UpdateTimeDto;
import by.betrayal.scheduleservice.repository.TimeRepository;
import by.betrayal.scheduleservice.service.TimeService;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

    private final TimeRepository repository;

    @Override
    public PageableContainer<TimeFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        return null;
    }

    @Override
    public List<TimeFullDto> findAllByInstitutionId(Long institutionId) {
        return null;
    }

    @Override
    public TimeFullDto findById(Long id) {
        return null;
    }

    @Override
    public TimeFullDto create(CreateTimeDto dto) {
        return null;
    }

    @Override
    public TimeFullDto update(UpdateTimeDto dto) {
        return null;
    }

    @Override
    public TimeFullDto delete(Long id) {
        return null;
    }
}
