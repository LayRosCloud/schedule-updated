package by.betrayal.groupservice.service.impl;

import by.betrayal.groupservice.dto.faculty.CreateFacultyDto;
import by.betrayal.groupservice.dto.faculty.FacultyFullDto;
import by.betrayal.groupservice.dto.faculty.UpdateFacultyDto;
import by.betrayal.groupservice.entity.FacultyEntity;
import by.betrayal.groupservice.mapper.FacultyMapper;
import by.betrayal.groupservice.repository.FacultyRepository;
import by.betrayal.groupservice.service.FacultyService;
import by.betrayal.groupservice.utils.ExceptionUtils;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableFactory;
import by.betrayal.groupservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<FacultyFullDto> findAllByCorpus(Long corpusId, PageableOptions options) {
        var pageable = PageableFactory.getPageableAsc(options);
        var facultyPage = facultyRepository.findAllByCorpusId(corpusId, pageable);
        var faculties = mapper.mapToDto(facultyPage.getContent());
        return new PageableContainer<>(facultyPage.getTotalElements(), faculties);
    }

    @Override
    @Transactional(readOnly = true)
    public FacultyFullDto findById(Long id) {
        FacultyEntity faculty = findByIdOrThrowNotFoundException(id);
        return mapper.mapToDto(faculty);
    }

    @Override
    @Transactional
    public FacultyFullDto create(CreateFacultyDto dto) {
        var faculty = mapper.mapToEntity(dto);
        var savedFaculty = facultyRepository.save(faculty);
        return mapper.mapToDto(savedFaculty);
    }

    @Override
    @Transactional
    public FacultyFullDto update(UpdateFacultyDto dto) {
        var faculty = findByIdOrThrowNotFoundException(dto.getId());
        mapper.mapToEntity(faculty, dto);
        var savedFaculty = facultyRepository.save(faculty);
        return mapper.mapToDto(savedFaculty);
    }

    @Override
    @Transactional
    public FacultyFullDto delete(Long id) {
        var faculty = findByIdOrThrowNotFoundException(id);
        facultyRepository.delete(faculty);
        return mapper.mapToDto(faculty);
    }

    private FacultyEntity findByIdOrThrowNotFoundException(Long id) {
        return facultyRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Faculty", id)
        );
    }
}
