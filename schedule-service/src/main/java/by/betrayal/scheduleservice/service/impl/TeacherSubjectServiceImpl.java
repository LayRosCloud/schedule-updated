package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.teachersubject.CreateTeacherSubjectDto;
import by.betrayal.scheduleservice.dto.teachersubject.TeacherSubjectFullDto;
import by.betrayal.scheduleservice.dto.teachersubject.UpdateTeacherSubjectDto;
import by.betrayal.scheduleservice.service.TeacherSubjectService;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;

import java.util.List;

public class TeacherSubjectServiceImpl implements TeacherSubjectService {
    @Override
    public PageableContainer<TeacherSubjectFullDto> findAllByTeacherId(Long teacherId, PageableOptions options) {
        return null;
    }

    @Override
    public PageableContainer<TeacherSubjectFullDto> findAll(PageableOptions options) {
        return null;
    }

    @Override
    public List<TeacherSubjectFullDto> findAllByTeacherId(Long teacherId) {
        return null;
    }

    @Override
    public TeacherSubjectFullDto findById(Long id) {
        return null;
    }

    @Override
    public TeacherSubjectFullDto create(CreateTeacherSubjectDto dto) {
        return null;
    }

    @Override
    public TeacherSubjectFullDto update(UpdateTeacherSubjectDto dto) {
        return null;
    }

    @Override
    public TeacherSubjectFullDto delete(Long id) {
        return null;
    }
}
