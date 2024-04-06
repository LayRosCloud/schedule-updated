package by.betrayal.audienceservice.service;


import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;

import java.util.List;

public interface AudienceTypeService {

    TotalPageable<AudienceTypeFullDto> findAll(PageableOptions options);
    List<AudienceTypeFullDto> findAll();
    AudienceTypeFullDto findById(Short id);
    AudienceTypeFullDto create(AudienceTypeCreateDto dto);
    AudienceTypeFullDto update(AudienceTypeUpdateDto dto);
    AudienceTypeFullDto delete(Short id);
}
