package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.subject.CreateSubjectDto;
import by.betrayal.scheduleservice.dto.subject.SubjectFullDto;
import by.betrayal.scheduleservice.dto.subject.UpdateSubjectDto;
import by.betrayal.scheduleservice.entity.SubjectEntity;
import by.betrayal.scheduleservice.mapper.SubjectMapper;
import by.betrayal.scheduleservice.repository.SubjectRepository;
import by.betrayal.scheduleservice.service.SubjectService;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableFactory;
import by.betrayal.scheduleservice.utils.PageableOptions;
import by.betrayal.scheduleservice.utils.ThrowableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository repository;
    private final SubjectMapper mapper;

    @Override
    @Transactional
    public PageableContainer<SubjectFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        var pageable = PageableFactory.createPageable(options);

        var page = repository.findAllByInstitutionId(institutionId, pageable);

        var list = mapper.mapToDto(page.getContent());

        return new PageableContainer<>(page.getTotalElements(), list);
    }

    @Override
    @Transactional
    public SubjectFullDto findById(Long id) {
        var item = findSubjectByIdOrThrowNotFoundException(id);
        return mapper.mapToDto(item);
    }

    @Override
    @Transactional
    public SubjectFullDto create(CreateSubjectDto dto) {
        var item = mapper.mapToEntity(dto);

        var result = repository.save(item);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public SubjectFullDto update(UpdateSubjectDto dto) {
        return null;
    }

    @Override
    @Transactional
    public SubjectFullDto delete(Long id) {
        return null;
    }

    private SubjectEntity findSubjectByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Subject with id %s is not found", id)
        );
    }
}
