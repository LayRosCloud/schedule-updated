package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.subgroup.CreateSubgroupDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.subgroup.UpdateSubgroupDto;
import by.betrayal.scheduleservice.service.SubgroupService;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.betrayal.scheduleservice.utils.pageable.PageableContainer.HEADER_TOTAL_COUNT;

@RestController
@RequiredArgsConstructor
public class SubgroupController {

    public static final String ENDPOINT_FIND_ALL_BY_GROUP_ID = "v1/groups/{groupId}/subgroups";
    public static final String ENDPOINT_BY_ID = "v1/subgroups/{id}";
    public static final String ENDPOINT = "v1/subgroups";
    private final SubgroupService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_GROUP_ID)
    public ResponseEntity<List<SubgroupFullDto>> findAllByGroupId(
            @PathVariable Long groupId,
            @RequestParam(value = PageableContainer.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(value = PageableContainer.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null) {
            var items = service.findAllByGroupId(groupId);

            return new ResponseEntity<>(items, HttpStatus.OK);
        }

        page = page == null ? 1 : page;
        var options = new PageableOptions(limit, page);
        var container = service.findAllByGroupId(groupId, options);

        return ResponseEntity.ok()
                .header(HEADER_TOTAL_COUNT, container.totalCount().toString())
                .body(container.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<SubgroupFullDto> findById(@PathVariable Long id) {
        var subgroup = service.findById(id);
        return new ResponseEntity<>(subgroup, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<SubgroupFullDto> create(@Valid @RequestBody CreateSubgroupDto dto) {
        var subgroup = service.create(dto);
        return new ResponseEntity<>(subgroup, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<SubgroupFullDto> update(@Valid @RequestBody UpdateSubgroupDto dto) {
        var subgroup = service.update(dto);
        return new ResponseEntity<>(subgroup, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<SubgroupFullDto> delete(@PathVariable Long id) {
        var subgroup = service.delete(id);
        return new ResponseEntity<>(subgroup, HttpStatus.OK);
    }
}
