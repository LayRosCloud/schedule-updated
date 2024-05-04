package by.betrayal.scheduleservice.controller;

import by.betrayal.scheduleservice.dto.clazz.type.CreateTypeClassDto;
import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.type.UpdateTypeClassDto;
import by.betrayal.scheduleservice.service.TypeClassService;
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
public class TypeClassController {

    public static final String ENDPOINT_FIND_ALL_BY_INSTITUTION_ID = "v1/institutions/{institutionId}/classes/types";
    public static final String ENDPOINT_BY_ID = "v1/classes/types/{id}";
    public static final String ENDPOINT = "v1/classes/types";

    private final TypeClassService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_INSTITUTION_ID)
    public ResponseEntity<List<TypeClassFullDto>> findAll(
            @PathVariable Long institutionId,
            @RequestParam(value = PageableContainer.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(value = PageableContainer.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null) {
            var types = service.findAllByInstitutionId(institutionId);
            return new ResponseEntity<>(types, HttpStatus.OK);
        }
        page = page == null ? 1 : page;
        var pageable = new PageableOptions(limit, page);
        var types = service.findAllByInstitutionId(institutionId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, types.totalCount().toString())
                .body(types.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<TypeClassFullDto> findById(@PathVariable Long id) {
        var type = service.findById(id);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<TypeClassFullDto> create(@Valid @RequestBody CreateTypeClassDto dto) {
        var type = service.create(dto);
        return new ResponseEntity<>(type, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<TypeClassFullDto> update(@Valid @RequestBody UpdateTypeClassDto dto) {
        var type = service.update(dto);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<TypeClassFullDto> delete(@PathVariable Long id) {
        var type = service.delete(id);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }
}
