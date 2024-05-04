package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.TimeFullDto;
import by.betrayal.scheduleservice.dto.time.UpdateTimeDto;
import by.betrayal.scheduleservice.service.TimeService;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeController {

    public static final String ENDPOINT_FIND_ALL_BY_INSTITUTION_ID = "v1/institutions/{institutionId}/times";
    public static final String ENDPOINT_BY_ID = "v1/times/{id}";
    public static final String ENDPOINT = "v1/times";

    private final TimeService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_INSTITUTION_ID)
    public ResponseEntity<List<TimeFullDto>> findAllByInstitutionId(
            @PathVariable Long institutionId,
            @RequestParam(value = PageableContainer.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(value = PageableContainer.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null) {
            var times = service.findAllByInstitutionId(institutionId);
            return new ResponseEntity<>(times, HttpStatus.OK);
        }

        page = page == null ? 1 : page;
        var pageable = new PageableOptions(limit, page);

        var timesPage = service.findAllByInstitutionId(institutionId, pageable);

        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, timesPage.totalCount().toString())
                .body(timesPage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<TimeFullDto> findById(@PathVariable Long id) {
        var time = service.findById(id);
        return new ResponseEntity<>(time, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<TimeFullDto> create(@Valid @RequestBody CreateTimeDto dto) {
        var time = service.create(dto);
        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<TimeFullDto> update(@Valid @RequestBody UpdateTimeDto dto) {
        var time = service.update(dto);
        return new ResponseEntity<>(time, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<TimeFullDto> delete(@PathVariable Long id) {
        var time = service.delete(id);
        return new ResponseEntity<>(time, HttpStatus.OK);
    }
}
