package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.subject.CreateSubjectDto;
import by.betrayal.scheduleservice.dto.subject.SubjectFullDto;
import by.betrayal.scheduleservice.dto.subject.UpdateSubjectDto;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;

public interface SubjectService {

    PageableContainer<SubjectFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options);
    SubjectFullDto findById(Long id);
    SubjectFullDto create(CreateSubjectDto dto);
    SubjectFullDto update(UpdateSubjectDto dto);
    SubjectFullDto delete(Long id);
}
