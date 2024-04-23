package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.teachersubject.CreateTeacherSubjectDto;
import by.betrayal.scheduleservice.dto.teachersubject.TeacherSubjectFullDto;
import by.betrayal.scheduleservice.dto.teachersubject.UpdateTeacherSubjectDto;
import by.betrayal.scheduleservice.entity.SubjectEntity;
import by.betrayal.scheduleservice.entity.TeacherSubjectEntity;
import by.betrayal.scheduleservice.mapper.TeacherSubjectMapper;
import by.betrayal.scheduleservice.repository.SubjectRepository;
import by.betrayal.scheduleservice.repository.TeacherSubjectRepository;
import by.betrayal.scheduleservice.service.TeacherSubjectService;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableFactory;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
import by.betrayal.scheduleservice.utils.ThrowableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherSubjectServiceImpl implements TeacherSubjectService {

    private final TeacherSubjectRepository repository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<TeacherSubjectFullDto> findAllByTeacherId(Long teacherId, PageableOptions options) {
        var pageable = PageableFactory.createPageable(options);
        var itemPage = repository.findAllByTeacherId(teacherId, pageable);
        var dtos = mapper.mapToDto(itemPage.getContent());
        return new PageableContainer<>(itemPage.getTotalElements(), dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherSubjectFullDto> findAllByTeacherId(Long teacherId) {
        var items = repository.findAllByTeacherId(teacherId);
        return mapper.mapToDto(items);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherSubjectFullDto findById(Long id) {
        var item = findByIdOrThrowNotFoundException(id);
        return mapper.mapToDto(item);
    }

    @Override
    @Transactional
    public TeacherSubjectFullDto create(CreateTeacherSubjectDto dto) {
        var subject = findByIdSubjectOrThrowNotFoundException(dto.getSubjectId());

        var item = mapper.mapToEntity(dto);
        item.setSubject(subject);

        var result = repository.save(item);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public TeacherSubjectFullDto update(UpdateTeacherSubjectDto dto) {
        var teacherSubject = findByIdOrThrowNotFoundException(dto.getId());
        var subject = findByIdSubjectOrThrowNotFoundException(dto.getSubjectId());

        mapper.mapToEntity(teacherSubject, dto);
        teacherSubject.setSubject(subject);

        var result = repository.save(teacherSubject);

        return mapper.mapToDto(result);
    }

    @Override
    public TeacherSubjectFullDto delete(Long id) {
        var teacherSubject = findByIdOrThrowNotFoundException(id);

        repository.delete(teacherSubject);

        return mapper.mapToDto(teacherSubject);
    }

    private TeacherSubjectEntity findByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Teacher subject with id %s is not found", id)
        );
    }

    private SubjectEntity findByIdSubjectOrThrowNotFoundException(Long id) {
        return subjectRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Subject with id %s is not found", id)
        );
    }
}
