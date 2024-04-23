package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.subgroup.CreateSubgroupDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.subgroup.UpdateSubgroupDto;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;

import java.util.List;

public interface SubgroupService {

    PageableContainer<SubgroupFullDto> findAllByGroupId(Long groupId, PageableOptions options);
    List<SubgroupFullDto> findAllByGroupId(Long groupId);
    SubgroupFullDto findById(Long id);
    SubgroupFullDto create(CreateSubgroupDto dto);
    SubgroupFullDto update(UpdateSubgroupDto dto);
    SubgroupFullDto delete(Long id);
}
