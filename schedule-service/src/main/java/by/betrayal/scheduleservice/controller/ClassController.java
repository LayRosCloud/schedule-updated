package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.clazz.ClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.CreateClassDto;
import by.betrayal.scheduleservice.dto.clazz.UpdateClassDto;
import by.betrayal.scheduleservice.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassController {

    private static final String ENDPOINT_FIND_ALL_BY_AUDIENCE_ID = "v1/audiences/{audienceId}/classes";
    private static final String ENDPOINT_FIND_ALL_BY_TEACHER_ID = "v1/teachers/{teacherId}/classes";
    private static final String ENDPOINT_FIND_ALL_BY_SUBGROUP_ID = "v1/subgroups/{subgroupId}/classes";
    private static final String ENDPOINT_BY_ID = "v1/classes/{id}";
    private static final String ENDPOINT = "v1/classes";
    private static final String ENDPOINT_CREATE = "v1/classes/range";

    private final ClassService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_AUDIENCE_ID)
    public ResponseEntity<List<ClassFullDto>> findAllByAudienceId(@PathVariable Long audienceId,
                                                                  @RequestParam(value = "date_start") Date dateStart,
                                                                  @RequestParam(value = "date_end") Date dateEnd
    ) {
        var classes = service.findAllByAudienceId(audienceId, dateStart, dateEnd);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_FIND_ALL_BY_TEACHER_ID)
    public ResponseEntity<List<ClassFullDto>> findAllByTeacherId(@PathVariable Long teacherId,
                                                                  @RequestParam(value = "date_start") Date dateStart,
                                                                  @RequestParam(value = "date_end") Date dateEnd
    ) {
        var classes = service.findAllByTeacherId(teacherId, dateStart, dateEnd);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_FIND_ALL_BY_SUBGROUP_ID)
    public ResponseEntity<List<ClassFullDto>> findAllBySubgroupId(@PathVariable Long subgroupId,
                                                                 @RequestParam(value = "date_start") Date dateStart,
                                                                 @RequestParam(value = "date_end") Date dateEnd
    ) {
        var classes = service.findAllByTeacherId(subgroupId, dateStart, dateEnd);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<ClassFullDto> findById(@PathVariable Long id) {
        var clazz = service.findById(id);
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<ClassFullDto> create(@RequestBody CreateClassDto dto) {
        var clazz = service.create(dto);
        return new ResponseEntity<>(clazz, HttpStatus.CREATED);
    }

    @PostMapping(ENDPOINT_CREATE)
    public ResponseEntity<List<ClassFullDto>> createRange(@RequestBody List<CreateClassDto> dtos) {
        var classes = service.createRange(dtos);
        return new ResponseEntity<>(classes, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<ClassFullDto> update(@RequestBody UpdateClassDto dto) {
        var clazz = service.update(dto);
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<ClassFullDto> delete(@PathVariable Long id) {
        var clazz = service.delete(id);
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

}
