package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import by.betrayal.audienceservice.dto.event.EventUpdateDto;
import by.betrayal.audienceservice.service.EventService;
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
public class EventController {

    private final EventService eventService;

    public static final String ENDPOINT_FIND_ALL = "v1/audiences/{audienceId}/events";
    public static final String ENDPOINT_BY_ID = "v1/events/{id}";
    public static final String ENDPOINT = "v1/events";

    @GetMapping(ENDPOINT_FIND_ALL)
    public ResponseEntity<List<EventFullDto>> findAll(
            @PathVariable Long audienceId,
            @RequestParam(name = Pagination.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(name = Pagination.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null || page == null) {
            var events = eventService.findAll(audienceId);
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
        var options = new PageableOptions(limit, page);

        var events = eventService.findAll(audienceId, options);

        return ResponseEntity.ok()
                .header(Pagination.HEADER_TOTAL_COUNT, String.valueOf(events.totalCount()))
                .body(events.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<EventFullDto> findById(@PathVariable Long id) {
        var item = eventService.findById(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<EventFullDto> create(@Valid @RequestBody EventCreateDto dto) {
        var item = eventService.create(dto);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<EventFullDto> update(@Valid @RequestBody EventUpdateDto dto) {
        var item = eventService.update(dto);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<EventFullDto> delete(@PathVariable Long id) {
        var item = eventService.delete(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
