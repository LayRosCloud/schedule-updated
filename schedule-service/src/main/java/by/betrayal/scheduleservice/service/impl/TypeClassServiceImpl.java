package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.clazz.type.CreateTypeClassDto;
import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.type.UpdateTypeClassDto;
import by.betrayal.scheduleservice.entity.TypeClassEntity;
import by.betrayal.scheduleservice.mapper.TypeClassMapper;
import by.betrayal.scheduleservice.repository.TypeClassRepository;
import by.betrayal.scheduleservice.service.TypeClassService;
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
public class TypeClassServiceImpl implements TypeClassService {

    private final TypeClassRepository repository;
    private final TypeClassMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<TypeClassFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        var pageable = PageableFactory.createPageable(options);
        var typePage = repository.findAllByInstitutionId(institutionId, pageable);
        var types = mapper.mapToDto(typePage.getContent());
        return new PageableContainer<>(typePage.getTotalElements(), types);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeClassFullDto> findAllByInstitutionId(Long institutionId) {
        var types = repository.findAllByInstitutionId(institutionId);
        return mapper.mapToDto(types);
    }

    @Override
    @Transactional(readOnly = true)
    public TypeClassFullDto findById(Long id) {
        var type = findByIdOrThrowNotFoundException(id);
        return mapper.mapToDto(type);
    }

    @Override
    @Transactional
    public TypeClassFullDto create(CreateTypeClassDto dto) {
        var type = mapper.mapToEntity(dto);

        var result = repository.save(type);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public TypeClassFullDto update(UpdateTypeClassDto dto) {
        var type = findByIdOrThrowNotFoundException(dto.getId());

        var result = repository.save(type);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public TypeClassFullDto delete(Long id) {
        var type = findByIdOrThrowNotFoundException(id);

        repository.delete(type);

        return mapper.mapToDto(type);
    }

    private TypeClassEntity findByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Type with id %s is not found", id)
        );
    }
}
