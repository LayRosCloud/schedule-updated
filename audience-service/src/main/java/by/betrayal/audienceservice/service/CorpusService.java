package by.betrayal.audienceservice.service;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusUpdateDto;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;

import java.util.List;

public interface CorpusService {

    TotalPageable<CorpusFullDto> findAll(Long institutionId, PageableOptions options);
    List<CorpusFullDto> findAll(Long institutionId);
    CorpusFullDto findById(Long id);
    CorpusFullDto create(CorpusCreateDto dto);
    CorpusFullDto update(CorpusUpdateDto dto);
    CorpusFullDto delete(Long id);
}
