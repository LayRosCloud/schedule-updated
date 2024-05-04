package by.betrayal.audienceservice.service.impl;

import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import by.betrayal.audienceservice.dto.institution.InstitutionUpdateDto;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import by.betrayal.audienceservice.mapper.InstitutionMapper;
import by.betrayal.audienceservice.repository.InstitutionRepository;
import by.betrayal.audienceservice.service.InstitutionService;
import by.betrayal.audienceservice.utils.DateUtils;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.TotalPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public TotalPageable<InstitutionFullDto> findAll(PageableOptions options) {
        var sort = Sort.by("name").ascending();

        var pageable = PageRequest.of(options.page(), options.limit(), sort);
        var institutionPage = institutionRepository.findAll(pageable);

        var institutions = mapper.mapToFullDto(institutionPage.toList());
        return new TotalPageable<>(institutions, institutionPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstitutionFullDto> findAll() {
        var institutions = institutionRepository.findAll();
        return mapper.mapToFullDto(institutions);
    }

    @Override
    @Transactional(readOnly = true)
    public InstitutionFullDto findById(Long id) {
        var institution = findByIdOrThrowNotFoundException(id);
        return mapper.mapToFullDto(institution);
    }

    @Override
    @Transactional
    public InstitutionFullDto create(InstitutionCreateDto dto) {
        var item = mapper.mapToEntity(dto);

        var institution = institutionRepository.save(item);

        return mapper.mapToFullDto(institution);
    }

    @Override
    public InstitutionFullDto update(InstitutionUpdateDto dto) {
        var institution = findByIdOrThrowNotFoundException(dto.getId());

        mapper.mapToEntity(institution, dto);

        var result = institutionRepository.save(institution);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public InstitutionFullDto delete(Long id) {
        var institution = findByIdOrThrowNotFoundException(id);

        institution.setDeletedAt(institution.getDeletedAt() == null ? DateUtils.getUtcTicks() : null);

        var result = institutionRepository.save(institution);
        return mapper.mapToFullDto(result);
    }

    private InstitutionEntity findByIdOrThrowNotFoundException(Long id) {
        return institutionRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Institution %d is not found", id)
        );
    }
}
