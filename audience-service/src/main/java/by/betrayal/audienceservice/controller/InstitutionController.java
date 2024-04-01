package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import by.betrayal.audienceservice.dto.institution.InstitutionUpdateDto;
import by.betrayal.audienceservice.service.InstitutionService;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping
    public ResponseEntity<List<InstitutionFullDto>> findAll(
            @RequestParam(name = Pagination.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(name = Pagination.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null || page == null) {
            var institutions = institutionService.findAll();
            return new ResponseEntity<>(institutions, HttpStatus.OK);
        }

        var options = new PageableOptions(limit, page - 1);
        var institutions = institutionService.findAll(options);

        return ResponseEntity.ok()
                .header(Pagination.HEADER_TOTAL_COUNT, String.valueOf(institutions.totalCount()))
                .body(institutions.items());
    }

    @GetMapping("{id}")
    public ResponseEntity<InstitutionFullDto> findById(@PathVariable Long id) {
        var institution = institutionService.findById(id);

        return new ResponseEntity<>(institution, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InstitutionFullDto> create(@RequestBody InstitutionCreateDto dto) {
        var institution = institutionService.create(dto);

        return new ResponseEntity<>(institution, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<InstitutionFullDto> update(@RequestBody InstitutionUpdateDto dto) {
        var institution = institutionService.update(dto);

        return new ResponseEntity<>(institution, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<InstitutionFullDto> delete(@PathVariable Long id) {
        var institution = institutionService.delete(id);

        return new ResponseEntity<>(institution, HttpStatus.OK);
    }
}
