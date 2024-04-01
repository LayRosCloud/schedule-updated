package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.service.AudienceService;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AudienceController {

    private final AudienceService service;

    public static final String ENDPOINT_FIND_ALL = "v1/housings/{corpusId}/audiences";
    public static final String ENDPOINT_BY_ID = "v1/audiences/{id}";
    public static final String ENDPOINT = "v1/audiences";

    @GetMapping(ENDPOINT_FIND_ALL)
    public ResponseEntity<List<AudienceFullDto>> findAll(
            @PathVariable Long corpusId,
           @RequestParam(name = Pagination.PARAM_LIMIT, required = false) Integer limit,
           @RequestParam(name = Pagination.PARAM_PAGE, required = false) Integer page

    ) {
        if (limit == null || page == null) {
            var audiences = service.findAll(corpusId);
            return new ResponseEntity<>(audiences, HttpStatus.OK);
        }

        var options = new PageableOptions(limit, page - 1);
        var audiences = service.findAll(corpusId, options);

        return ResponseEntity.ok()
                .header(Pagination.HEADER_TOTAL_COUNT, String.valueOf(audiences.totalCount()))
                .body(audiences.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<AudienceFullDto> findById(@PathVariable Long id) {
        var item = service.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<AudienceFullDto> create(@RequestBody AudienceCreateDto audience) {
        var item = service.create(audience);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<AudienceFullDto> update(@RequestBody AudienceUpdateDto audience) {
        var item = service.update(audience);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<AudienceFullDto> delete(@PathVariable Long id) {
        var item = service.delete(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
