package by.betrayal.audienceservice.service.impl;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.mapper.AudienceMapper;
import by.betrayal.audienceservice.repository.AudienceRepository;
import by.betrayal.audienceservice.repository.AudienceTypeRepository;
import by.betrayal.audienceservice.repository.CorpusRepository;
import by.betrayal.audienceservice.service.AudienceService;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;
    private final CorpusRepository corpusRepository;
    private final AudienceTypeRepository audienceTypeRepository;
    private final AudienceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public TotalPageable<AudienceFullDto> findAll(Long corpusId, PageableOptions options) {
        var corpus = findCorpusOrThrowNotFoundException(corpusId);

        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(options.page(), options.limit(), sort);

        var audiencePage = audienceRepository.findAllByCorpus(corpus, pageable);
        var audiences = mapper.mapToFullDto(audiencePage.toList());

        return new TotalPageable<>(audiences, audiencePage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AudienceFullDto> findAll(Long corpusId) {
        var corpus = findCorpusOrThrowNotFoundException(corpusId);
        var audiences = audienceRepository.findAllByCorpus(corpus);
        return mapper.mapToFullDto(audiences);
    }

    @Override
    @Transactional(readOnly = true)
    public AudienceFullDto findById(Long id) {
        var audience = findAudienceOrThrowNotFoundException(id);
        return mapper.mapToFullDto(audience);
    }

    @Override
    @Transactional
    public AudienceFullDto create(@Valid AudienceCreateDto dto) {
        var corpus = findCorpusOrThrowNotFoundException(dto.getCorpusId());
        var type = findAudienceTypeOrThrowNotFoundException(dto.getTypeId());

        var audience = mapper.mapToEntity(dto);
        audience.setCorpus(corpus);
        audience.setType(type);

        var result = audienceRepository.save(audience);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public AudienceFullDto update(@Valid AudienceUpdateDto dto) {
        var audience = findAudienceOrThrowNotFoundException(dto.getId());
        var type = findAudienceTypeOrThrowNotFoundException(dto.getTypeId());
        var corpus = findCorpusOrThrowNotFoundException(dto.getCorpusId());

        mapper.mapToEntity(audience, dto);
        audience.setCorpus(corpus);
        audience.setType(type);

        var result = audienceRepository.save(audience);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public AudienceFullDto delete(Long id) {
        var audience = findAudienceOrThrowNotFoundException(id);

        audienceRepository.delete(audience);

        return mapper.mapToFullDto(audience);
    }

    private CorpusEntity findCorpusOrThrowNotFoundException(Long id) {
        return corpusRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Corpus with id %d not found", id)
        );
    }

    private AudienceEntity findAudienceOrThrowNotFoundException(Long id) {
        return audienceRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Audience with id %d not found", id)
        );
    }

    private AudienceTypeEntity findAudienceTypeOrThrowNotFoundException(Short id) {
        return audienceTypeRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Type with id %d not found", id)
        );
    }
}
