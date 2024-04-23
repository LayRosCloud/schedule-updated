package by.betrayal.scheduleservice.service;

import by.betrayal.scheduleservice.dto.clazz.type.CreateTypeClassDto;
import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.type.UpdateTypeClassDto;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;

import java.util.List;

public interface TypeClassService {

    PageableContainer<TypeClassFullDto> findAllByInstitutionId(Long institutionId, PageableOptions options);
    List<TypeClassFullDto> findAllByInstitutionId(Long institutionId);
    TypeClassFullDto findById(Long id);
    TypeClassFullDto create(CreateTypeClassDto dto);
    TypeClassFullDto update(UpdateTypeClassDto dto);
    TypeClassFullDto delete(Long id);
}
