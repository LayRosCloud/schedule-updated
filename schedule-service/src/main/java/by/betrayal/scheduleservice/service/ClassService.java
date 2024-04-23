package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.clazz.ClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.CreateClassDto;
import by.betrayal.scheduleservice.dto.clazz.UpdateClassDto;

import java.util.Date;
import java.util.List;

public interface ClassService {
    List<ClassFullDto> findAllByAudienceId(Long audienceId, Date start, Date end);
    List<ClassFullDto> findAllByTeacherId(Long teacherId, Date start, Date end);
    List<ClassFullDto> findAllBySubgroupId(Long subgroupId, Date start, Date end);
    ClassFullDto findById(Long id);
    ClassFullDto create(CreateClassDto dto);
    List<ClassFullDto> createRange(List<CreateClassDto> dtos);
    ClassFullDto update(UpdateClassDto dto);
    ClassFullDto delete(Long id);

}
