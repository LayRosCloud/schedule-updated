package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.subgroup.CreateSubgroupDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.subgroup.UpdateSubgroupDto;
import by.betrayal.scheduleservice.entity.SubgroupEntity;
import by.betrayal.scheduleservice.mapper.SubgroupMapper;
import by.betrayal.scheduleservice.repository.SubgroupRepository;
import by.betrayal.scheduleservice.service.SubgroupService;
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
public class SubgroupServiceImpl implements SubgroupService {

    private final SubgroupRepository repository;
    private final SubgroupMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<SubgroupFullDto> findAllByGroupId(Long groupId, PageableOptions options) {
        var pageable = PageableFactory.createPageable(options);
        var subgroupPage = repository.findAllByGroupId(groupId, pageable);
        var subgroupsDto = mapper.mapToDto(subgroupPage.getContent());
        return new PageableContainer<>(subgroupPage.getTotalElements(), subgroupsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubgroupFullDto> findAllByGroupId(Long groupId) {
        var subgroups = repository.findAllByGroupId(groupId);
        return mapper.mapToDto(subgroups);
    }

    @Override
    @Transactional(readOnly = true)
    public SubgroupFullDto findById(Long id) {
        SubgroupEntity subgroup = findByIdOrThrowNotFoundException(id);
        return mapper.mapToDto(subgroup);
    }

    @Override
    @Transactional
    public SubgroupFullDto create(CreateSubgroupDto dto) {
        var subgroup = mapper.mapToEntity(dto);

        var result = repository.save(subgroup);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public SubgroupFullDto update(UpdateSubgroupDto dto) {
        var subgroup = findByIdOrThrowNotFoundException(dto.getId());

        mapper.mapToEntity(subgroup, dto);

        return mapper.mapToDto(subgroup);
    }

    @Override
    @Transactional
    public SubgroupFullDto delete(Long id) {
        var subgroup = findByIdOrThrowNotFoundException(id);

        repository.delete(subgroup);

        return mapper.mapToDto(subgroup);
    }

    private SubgroupEntity findByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Subgroup with id %s is not found", id)
        );
    }
}
