package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.subject.CreateSubjectDto;
import by.betrayal.scheduleservice.dto.subject.SubjectFullDto;
import by.betrayal.scheduleservice.dto.subject.UpdateSubjectDto;
import by.betrayal.scheduleservice.entity.SubjectEntity;
import by.betrayal.scheduleservice.mapper.SubjectMapper;
import by.betrayal.scheduleservice.repository.SubjectRepository;
import by.betrayal.scheduleservice.service.SubjectService;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableFactory;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
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
    @Transactional(readOnly = true)
    public PageableContainer<SubjectFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        var pageable = PageableFactory.createPageable(options);

        var page = repository.findAllByInstitutionId(institutionId, pageable);

        var list = mapper.mapToDto(page.getContent());

        return new PageableContainer<>(page.getTotalElements(), list);
    }

    @Override
    @Transactional(readOnly = true)
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
        var item = findSubjectByIdOrThrowNotFoundException(dto.getId());

        mapper.mapToEntity(item, dto);

        var result = repository.save(item);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public SubjectFullDto delete(Long id) {
        var item = findSubjectByIdOrThrowNotFoundException(id);

        repository.delete(item);

        return mapper.mapToDto(item);
    }

    private SubjectEntity findSubjectByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Subject with id %s is not found", id)
        );
    }
}
