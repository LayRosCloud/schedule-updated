package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.teachersubject.CreateTeacherSubjectDto;
import by.betrayal.scheduleservice.dto.teachersubject.TeacherSubjectFullDto;
import by.betrayal.scheduleservice.dto.teachersubject.UpdateTeacherSubjectDto;
import by.betrayal.scheduleservice.service.TeacherSubjectService;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherSubjectController {

    public static final String ENDPOINT_FIND_ALL_BY_TEACHER_ID = "v1/teachers/{teacherId}/subjects";
    public static final String ENDPOINT_BY_ID = "v1/teachers/subjects/{id}";
    public static final String ENDPOINT = "v1/teachers/subjects";
    private final TeacherSubjectService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_TEACHER_ID)
    public ResponseEntity<List<TeacherSubjectFullDto>> findAllByTeacherId(
            @PathVariable Long teacherId,
            @RequestParam(value = PageableContainer.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(value = PageableContainer.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null) {
            var teachersSubjects = service.findAllByTeacherId(teacherId);
            return new ResponseEntity<>(teachersSubjects, HttpStatus.OK);
        }

        page = page == null ? 1 : page;
        var pageable = new PageableOptions(limit, page);

        var teachersSubjectsPage = service.findAllByTeacherId(teacherId, pageable);

        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, teachersSubjectsPage.totalCount().toString())
                .body(teachersSubjectsPage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<TeacherSubjectFullDto> findById(@PathVariable Long id) {
        var teacherSubject = service.findById(id);
        return new ResponseEntity<>(teacherSubject, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<TeacherSubjectFullDto> create(@RequestBody CreateTeacherSubjectDto dto) {
        var item = service.create(dto);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<TeacherSubjectFullDto> update(@RequestBody UpdateTeacherSubjectDto dto) {
        var item = service.update(dto);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<TeacherSubjectFullDto> delete(@PathVariable Long id) {
        var item = service.delete(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
