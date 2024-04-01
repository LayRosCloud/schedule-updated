package by.betrayal.audienceservice.service.impl;

import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import by.betrayal.audienceservice.mapper.AudienceTypeMapper;
import by.betrayal.audienceservice.repository.AudienceTypeRepository;
import by.betrayal.audienceservice.service.AudienceTypeService;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AudienceTypeServiceImpl implements AudienceTypeService {

    private final AudienceTypeRepository repository;
    private final AudienceTypeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public TotalPageable<AudienceTypeFullDto> findAll(PageableOptions options) {
        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(options.page(), options.limit(), sort);
        var typePage = repository.findAll(pageable);
        var types = mapper.mapToFullDto(typePage.toList());
        return new TotalPageable<>(types, typePage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AudienceTypeFullDto> findAll() {
        var types = repository.findAll();
        return mapper.mapToFullDto(types);
    }

    @Override
    @Transactional(readOnly = true)
    public AudienceTypeFullDto findById(Short id) {
        var type = findByIdOrThrowNotFoundException(id);
        return mapper.mapToFullDto(type);
    }

    @Override
    @Transactional
    public AudienceTypeFullDto create(@Valid AudienceTypeCreateDto dto) {
        var item = mapper.mapToEntity(dto);

        var type = repository.save(item);

        return mapper.mapToFullDto(type);
    }

    @Override
    @Transactional
    public AudienceTypeFullDto update(@Valid AudienceTypeUpdateDto dto) {
        var type = findByIdOrThrowNotFoundException(dto.getId());

        var result = repository.save(type);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public AudienceTypeFullDto delete(Short id) {
        var type = findByIdOrThrowNotFoundException(id);

        repository.delete(type);

        return mapper.mapToFullDto(type);
    }

    private AudienceTypeEntity findByIdOrThrowNotFoundException(Short id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Type with id %d not found exception", id)
        );
    }
}
