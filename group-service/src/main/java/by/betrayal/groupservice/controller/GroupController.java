package by.betrayal.groupservice.controller;

import by.betrayal.groupservice.dto.group.CreateGroupDto;
import by.betrayal.groupservice.dto.group.GroupFullDto;
import by.betrayal.groupservice.dto.group.UpdateGroupDto;
import by.betrayal.groupservice.service.GroupService;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {

    public static final String ENDPOINT_FIND_ALL_BY_COURSE_ID = "v1/courses/{courseId}/groups";
    public static final String ENDPOINT_BY_ID = "v1/groups/{id}";
    public static final String ENDPOINT = "v1/groups";
    public static final String ENDPOINT_RANGE = "v1/groups/range";

    private final GroupService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_COURSE_ID)
    public ResponseEntity<List<GroupFullDto>> findAllByCourse(
            @PathVariable Long courseId,
            @RequestParam(value = PageableContainer.PARAM_PAGE) Integer page,
            @RequestParam(value = PageableContainer.PARAM_LIMIT) Integer limit
    ) {
        var pageable = new PageableOptions(limit, page);
        var groupPage = service.findAllByCourse(courseId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, groupPage.totalCount().toString())
                .body(groupPage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<GroupFullDto> findById(@PathVariable Long id) {
        var group = service.findById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<GroupFullDto> create(@RequestBody CreateGroupDto dto) {
        var group = service.create(dto);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PostMapping(ENDPOINT_RANGE)
    public ResponseEntity<List<GroupFullDto>> create(@RequestBody List<CreateGroupDto> dtos) {
        var group = service.create(dtos);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<GroupFullDto> update(@RequestBody UpdateGroupDto dto) {
        var group = service.update(dto);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<GroupFullDto> delete(@PathVariable Long id) {
        var group = service.delete(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }
}
