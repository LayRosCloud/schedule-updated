package by.betrayal.requestservice.controller;

import by.betrayal.requestservice.dto.participant.CreateParticipantDto;
import by.betrayal.requestservice.dto.participant.ParticipantFullDto;
import by.betrayal.requestservice.dto.participant.UpdateParticipantDto;
import by.betrayal.requestservice.service.ParticipantService;
import by.betrayal.requestservice.utils.pageable.PageableContainer;
import by.betrayal.requestservice.utils.pageable.PageableOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

    public static final String ENDPOINT_FIND_ALL_BY_PERSON_ID = "v1/people/{personId}/requests";
    public static final String ENDPOINT_BY_ID = "v1/participants/{id}";
    public static final String ENDPOINT = "v1/participants";

    private final ParticipantService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_PERSON_ID)
    public ResponseEntity<List<ParticipantFullDto>> findAllByPersonId(
            @PathVariable Long personId,
            @RequestParam(value = PageableContainer.PARAM_LIMIT) Integer limit,
            @RequestParam(value = PageableContainer.PARAM_PAGE) Integer page
    ) {
        var pageable = new PageableOptions(limit, page);
        var requestsPage = service.findAllByPersonId(personId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, requestsPage.totalCount().toString())
                .body(requestsPage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<ParticipantFullDto> findById(@PathVariable Long id) {
        var request = service.findById(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<ParticipantFullDto> create(@Valid @RequestBody CreateParticipantDto dto) {
        var request = service.create(dto);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<ParticipantFullDto> update(@Valid @RequestBody UpdateParticipantDto dto) {
        var request = service.update(dto);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<ParticipantFullDto> delete(@PathVariable Long id) {
        var request = service.delete(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
