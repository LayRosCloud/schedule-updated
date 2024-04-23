package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.service.SubgroupService;
import by.betrayal.scheduleservice.utils.pageable.PageableContainer;
import by.betrayal.scheduleservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.betrayal.scheduleservice.utils.pageable.PageableContainer.HEADER_TOTAL_COUNT;

@RestController
@RequiredArgsConstructor
public class SubgroupController {

    private static final String ENDPOINT_FIND_ALL_BY_GROUP_ID = "v1/groups/{groupId}/subgroups";
    private static final String ENDPOINT_BY_ID = "v1/subgroups/{id}";
    private static final String ENDPOINT = "v1/subgroups";
    private final SubgroupService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_GROUP_ID)
    public ResponseEntity<List<SubgroupFullDto>> findAllByGroupId(@PathVariable Long groupId,
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
}
