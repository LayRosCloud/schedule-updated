package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.subject.CreateSubjectDto;
import by.betrayal.scheduleservice.dto.subject.SubjectFullDto;
import by.betrayal.scheduleservice.dto.subject.UpdateSubjectDto;
import by.betrayal.scheduleservice.service.SubjectService;
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
public class SubjectController {

    public static final String ENDPOINT_FIND_ALL_BY_INSTITUTION_ID = "v1/institutions/{institutionId}/subjects";
    public static final String ENDPOINT = "v1/subjects";
    public static final String ENDPOINT_BY_ID = "v1/subjects/{id}";

    private final SubjectService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_INSTITUTION_ID)
    public ResponseEntity<List<SubjectFullDto>> findAllByInstitutionId(
            @PathVariable Long institutionId,
            @RequestParam(value = PageableContainer.PARAM_PAGE) Integer page,
            @RequestParam(value = PageableContainer.PARAM_LIMIT) Integer limit
    ) {
        var pageable = new PageableOptions(limit, page);
        var subjectPage = service.findAllByInstitutionId(institutionId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, subjectPage.totalCount().toString())
                .body(subjectPage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<SubjectFullDto> findById(@PathVariable Long id) {
        var subject = service.findById(id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<SubjectFullDto> create(@Valid @RequestBody CreateSubjectDto dto) {
        var subject = service.create(dto);
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<SubjectFullDto> update(@Valid @RequestBody UpdateSubjectDto dto) {
        var subject = service.update(dto);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<SubjectFullDto> delete(@PathVariable Long id) {
        var subject = service.delete(id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }
}
