package by.betrayal.groupservice.controller;

import by.betrayal.groupservice.dto.course.CourseFullDto;
import by.betrayal.groupservice.dto.course.CreateCourseDto;
import by.betrayal.groupservice.dto.course.UpdateCourseDto;
import by.betrayal.groupservice.service.CourseService;
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
public class EventController {

    public static final String ENDPOINT_FIND_ALL_BY_FACULTY_ID = "v1/groups/{groupId}/events";
    public static final String ENDPOINT_BY_ID = "v1/events/{id}";
    public static final String ENDPOINT = "v1/events";

    private final CourseService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_FACULTY_ID)
    public ResponseEntity<List<CourseFullDto>> findAllByGroup(
            @PathVariable Long groupId,
            @RequestParam(value = PageableContainer.PARAM_PAGE) Integer page,
            @RequestParam(value = PageableContainer.PARAM_LIMIT) Integer limit
    ) {
        var pageable = new PageableOptions(limit, page);
        var coursePage = service.findAllByFaculty(groupId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, coursePage.totalCount().toString())
                .body(coursePage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<CourseFullDto> findById(@PathVariable Long id) {
        var group = service.findById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<CourseFullDto> create(@Valid @RequestBody CreateCourseDto dto) {
        var group = service.create(dto);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<CourseFullDto> update(@Valid @RequestBody UpdateCourseDto dto) {
        var group = service.update(dto);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<CourseFullDto> delete(@PathVariable Long id) {
        var group = service.delete(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }
}
