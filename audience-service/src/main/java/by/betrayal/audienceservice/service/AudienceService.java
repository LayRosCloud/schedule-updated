package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;

import java.util.List;

public interface AudienceService {
    TotalPageable<AudienceFullDto> findAll(Long corpusId, PageableOptions options);
    List<AudienceFullDto> findAll(Long corpusId);
    AudienceFullDto findById(Long id);
    AudienceFullDto create(AudienceCreateDto dto);
    AudienceFullDto update(AudienceUpdateDto dto);
    AudienceFullDto delete(Long id);
}
