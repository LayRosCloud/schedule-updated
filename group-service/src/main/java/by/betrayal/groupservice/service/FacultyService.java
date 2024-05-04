package by.betrayal.groupservice.service;

import by.betrayal.groupservice.dto.faculty.CreateFacultyDto;
import by.betrayal.groupservice.dto.faculty.FacultyFullDto;
import by.betrayal.groupservice.dto.faculty.UpdateFacultyDto;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableOptions;

public interface FacultyService {

    PageableContainer<FacultyFullDto> findAllByCorpus(Long corpusId, PageableOptions options);
    FacultyFullDto findById(Long id);
    FacultyFullDto create(CreateFacultyDto dto);
    FacultyFullDto update(UpdateFacultyDto dto);
    FacultyFullDto delete(Long id);
}
