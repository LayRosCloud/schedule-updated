package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.subgroup.CreateSubgroupDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.subgroup.UpdateSubgroupDto;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;

import java.util.List;

public interface SubgroupService {

    PageableContainer<SubgroupFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options);
    List<SubgroupFullDto> findAllByInstitutionId(Long institutionId);
    SubgroupFullDto findById(Long id);
    SubgroupFullDto create(CreateSubgroupDto dto);
    SubgroupFullDto update(UpdateSubgroupDto dto);
    SubgroupFullDto delete(Long id);
}
