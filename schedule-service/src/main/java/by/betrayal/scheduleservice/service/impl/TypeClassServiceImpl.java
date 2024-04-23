package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.UpdateTimeDto;
import by.betrayal.scheduleservice.service.TypeClassService;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;

import java.util.List;

public class TypeClassServiceImpl implements TypeClassService {
    @Override
    public PageableContainer<TypeClassFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options) {
        return null;
    }

    @Override
    public List<TypeClassFullDto> findAllByInstitutionId(Long institutionId) {
        return null;
    }

    @Override
    public TypeClassFullDto findById(Long id) {
        return null;
    }

    @Override
    public TypeClassFullDto create(CreateTimeDto dto) {
        return null;
    }

    @Override
    public TypeClassFullDto update(UpdateTimeDto dto) {
        return null;
    }

    @Override
    public TypeClassFullDto delete(Long id) {
        return null;
    }
}
