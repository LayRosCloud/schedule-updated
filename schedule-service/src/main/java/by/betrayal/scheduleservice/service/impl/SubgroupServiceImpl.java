package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.subgroup.CreateSubgroupDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.subgroup.UpdateSubgroupDto;
import by.betrayal.scheduleservice.service.SubgroupService;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;

import java.util.List;

public class SubgroupServiceImpl implements SubgroupService {
    @Override
    public PageableContainer<SubgroupFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        return null;
    }

    @Override
    public List<SubgroupFullDto> findAllByInstitutionId(Long institutionId) {
        return null;
    }

    @Override
    public SubgroupFullDto findById(Long id) {
        return null;
    }

    @Override
    public SubgroupFullDto create(CreateSubgroupDto dto) {
        return null;
    }

    @Override
    public SubgroupFullDto update(UpdateSubgroupDto dto) {
        return null;
    }

    @Override
    public SubgroupFullDto delete(Long id) {
        return null;
    }
}
