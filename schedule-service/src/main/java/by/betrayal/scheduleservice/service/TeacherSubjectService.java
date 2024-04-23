package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.teachersubject.CreateTeacherSubjectDto;
import by.betrayal.scheduleservice.dto.teachersubject.TeacherSubjectFullDto;
import by.betrayal.scheduleservice.dto.teachersubject.UpdateTeacherSubjectDto;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;

import java.util.List;

public interface TeacherSubjectService {

    PageableContainer<TeacherSubjectFullDto> findAllByTeacherId(Long teacherId, PageableOptions options);
    List<TeacherSubjectFullDto> findAllByTeacherId(Long teacherId);
    TeacherSubjectFullDto findById(Long id);
    TeacherSubjectFullDto create(CreateTeacherSubjectDto dto);
    TeacherSubjectFullDto update(UpdateTeacherSubjectDto dto);
    TeacherSubjectFullDto delete(Long id);
}
