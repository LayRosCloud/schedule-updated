package by.betrayal.audienceservice.service.impl;

import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusUpdateDto;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import by.betrayal.audienceservice.mapper.CorpusMapper;
import by.betrayal.audienceservice.repository.CorpusRepository;
import by.betrayal.audienceservice.repository.InstitutionRepository;
import by.betrayal.audienceservice.service.CorpusService;
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
public class CorpusServiceImpl implements CorpusService {

    private final CorpusRepository corpusRepository;
    private final InstitutionRepository institutionRepository;
    private final CorpusMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public TotalPageable<CorpusFullDto> findAll(Long institutionId, PageableOptions options) {
        var institution = findByIdInstitutionOrThrowNotFoundException(institutionId);
        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(options.page(), options.limit(), sort);
        var corpusPage = corpusRepository.findAllByInstitution(institution, pageable);
        var corpusList = mapper.mapToFullDto(corpusPage.toList());

        return new TotalPageable<>(corpusList, corpusPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CorpusFullDto> findAll(Long institutionId) {
        var institution = findByIdInstitutionOrThrowNotFoundException(institutionId);
        var corpusList = corpusRepository.findAllByInstitution(institution);
        return mapper.mapToFullDto(corpusList);
    }

    @Override
    @Transactional(readOnly = true)
    public CorpusFullDto findById(Long id) {
        var corpus = findByIdCorpusOrThrowNotFoundException(id);
        return mapper.mapToFullDto(corpus);
    }

    @Override
    @Transactional
    public CorpusFullDto create(CorpusCreateDto dto) {
        var institution = findByIdInstitutionOrThrowNotFoundException(dto.getInstitutionId());
        var corpus = mapper.mapToEntity(dto);
        corpus.setInstitution(institution);

        var result = corpusRepository.save(corpus);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public CorpusFullDto update(CorpusUpdateDto dto) {
        var corpus = findByIdCorpusOrThrowNotFoundException(dto.getId());
        var institution = findByIdInstitutionOrThrowNotFoundException(dto.getInstitutionId());
        mapper.mapToEntity(corpus, dto);
        corpus.setInstitution(institution);

        var result = corpusRepository.save(corpus);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public CorpusFullDto delete(Long id) {
        var corpus = findByIdCorpusOrThrowNotFoundException(id);

        corpusRepository.delete(corpus);

        return mapper.mapToFullDto(corpus);
    }

    private CorpusEntity findByIdCorpusOrThrowNotFoundException(Long id) {
        return corpusRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Corpus with id %d is not found", id)
        );
    }

    private InstitutionEntity findByIdInstitutionOrThrowNotFoundException(Long id) {
        return institutionRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Institution with id %d is not found", id)
        );
    }
}
