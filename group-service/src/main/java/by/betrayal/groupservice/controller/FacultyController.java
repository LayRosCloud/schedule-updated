package by.betrayal.groupservice.controller;

import by.betrayal.groupservice.dto.faculty.CreateFacultyDto;
import by.betrayal.groupservice.dto.faculty.FacultyFullDto;
import by.betrayal.groupservice.dto.faculty.UpdateFacultyDto;
import by.betrayal.groupservice.service.FacultyService;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FacultyController {

    public static final String ENDPOINT_FIND_ALL_BY_FACULTY_ID = "v1/corpuses/{corpusId}/faculties";
    public static final String ENDPOINT_BY_ID = "v1/faculties/{id}";
    public static final String ENDPOINT = "v1/faculties";

    private final FacultyService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_FACULTY_ID)
    public ResponseEntity<List<FacultyFullDto>> findAllByCorpus(
            @PathVariable Long corpusId,
            @RequestParam(value = PageableContainer.PARAM_PAGE) Integer page,
            @RequestParam(value = PageableContainer.PARAM_LIMIT) Integer limit
    ) {
        var pageable = new PageableOptions(limit, page);
        var coursePage = service.findAllByCorpus(corpusId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, coursePage.totalCount().toString())
                .body(coursePage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<FacultyFullDto> findById(@PathVariable Long id) {
        var group = service.findById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<FacultyFullDto> create(@Valid @RequestBody CreateFacultyDto dto) {
        var group = service.create(dto);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<FacultyFullDto> update(@Valid @RequestBody UpdateFacultyDto dto) {
        var faculty = service.update(dto);
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<FacultyFullDto> delete(@PathVariable Long id) {
        var group = service.delete(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }
}
