package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.UpdateTimeDto;
import by.betrayal.scheduleservice.utils.PageableContainer;
import by.betrayal.scheduleservice.utils.PageableOptions;

import java.util.List;

public interface TypeClassService {

    PageableContainer<TypeClassFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options);
    List<TypeClassFullDto> findAllByInstitutionId(Long institutionId);
    TypeClassFullDto findById(Long id);
    TypeClassFullDto create(CreateTimeDto dto);
    TypeClassFullDto update(UpdateTimeDto dto);
    TypeClassFullDto delete(Long id);
}
