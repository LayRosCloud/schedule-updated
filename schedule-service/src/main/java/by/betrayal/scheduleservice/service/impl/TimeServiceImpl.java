package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.TimeFullDto;
import by.betrayal.scheduleservice.dto.time.UpdateTimeDto;
import by.betrayal.scheduleservice.entity.TimeEntity;
import by.betrayal.scheduleservice.mapper.TimeMapper;
import by.betrayal.scheduleservice.repository.TimeRepository;
import by.betrayal.scheduleservice.service.TimeService;
import by.betrayal.scheduleservice.utils.ThrowableUtils;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableFactory;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

    private final TimeRepository repository;
    private final TimeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<TimeFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        var pageable = PageableFactory.createPageable(options);
        var timePage = repository.findAllByInstitutionId(institutionId, pageable);
        var times = mapper.mapToDto(timePage.getContent());

        return new PageableContainer<>(timePage.getTotalElements(), times);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeFullDto> findAllByInstitutionId(Long institutionId) {
        var times = repository.findAllByInstitutionId(institutionId);
        return mapper.mapToDto(times);
    }

    @Override
    @Transactional(readOnly = true)
    public TimeFullDto findById(Long id) {
        var item = findByIdTimeOrThrowNotFoundException(id);
        return mapper.mapToDto(item);
    }

    @Override
    @Transactional
    public TimeFullDto create(CreateTimeDto dto) {

        if (isAfter(dto.getTimeStart(), dto.getTimeEnd())) {
            throwTimeRequestException();
        }

        var item = mapper.mapToEntity(dto);

        var result = repository.save(item);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public TimeFullDto update(UpdateTimeDto dto) {
        if (isAfter(dto.getTimeStart(), dto.getTimeEnd())) {
            throwTimeRequestException();
        }

        var item = findByIdTimeOrThrowNotFoundException(dto.getId());
        mapper.mapToEntity(item, dto);
        var result = repository.save(item);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public TimeFullDto delete(Long id) {
        var item = findByIdTimeOrThrowNotFoundException(id);

        repository.delete(item);

        return mapper.mapToDto(item);
    }

    private TimeEntity findByIdTimeOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Time with id %s is not found", id)
        );
    }

    private static void throwTimeRequestException() {
        throw ThrowableUtils.getBadRequestException("time start was longer time end");
    }

    private static boolean isAfter(Time timeStart, Time timeEnd) {
        return timeStart.after(timeEnd);
    }
}
