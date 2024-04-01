package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import by.betrayal.audienceservice.dto.institution.InstitutionUpdateDto;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;

import java.util.List;

public interface InstitutionService {
    TotalPageable<InstitutionFullDto> findAll(PageableOptions options);
    List<InstitutionFullDto> findAll();
    InstitutionFullDto findById(Long id);
    InstitutionFullDto create(InstitutionCreateDto dto);
    InstitutionFullDto update(InstitutionUpdateDto dto);
    InstitutionFullDto delete(Long id);
}
