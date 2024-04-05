package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusUpdateDto;
import by.betrayal.audienceservice.service.CorpusService;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CorpusController {

    private final CorpusService service;
    public static final String ENDPOINT_FIND_ALL = "v1/institutions/{institutionId}/housings";
    public static final String ENDPOINT_BY_ID = "v1/housings/{id}";
    public static final String ENDPOINT = "v1/housings";

    @GetMapping(ENDPOINT_FIND_ALL)
    public ResponseEntity<List<CorpusFullDto>> findAll(
            @PathVariable Long institutionId,
            @RequestParam(name = Pagination.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(name = Pagination.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null || page == null) {
            var housings = service.findAll(institutionId);
            return new ResponseEntity<>(housings, HttpStatus.OK);
        }

        var options = new PageableOptions(limit, page - 1);

        var housings = service.findAll(institutionId, options);

        return ResponseEntity.ok()
                .header(Pagination.HEADER_TOTAL_COUNT, housings.totalCount().toString())
                .body(housings.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<CorpusFullDto> findById(@PathVariable Long id) {
        var corpus = service.findById(id);
        return new ResponseEntity<>(corpus, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<CorpusFullDto> create(@Valid @RequestBody CorpusCreateDto dto) {
        var corpus = service.create(dto);
        return new ResponseEntity<>(corpus, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<CorpusFullDto> update(@Valid @RequestBody CorpusUpdateDto dto) {
        var corpus = service.update(dto);
        return new ResponseEntity<>(corpus, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<CorpusFullDto> update(@PathVariable Long id) {
        var corpus = service.delete(id);
        return new ResponseEntity<>(corpus, HttpStatus.OK);
    }

}
