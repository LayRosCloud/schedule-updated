package by.betrayal.audienceservice.controller;

import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.service.AudienceTypeService;
import by.betrayal.audienceservice.utils.pagination.PageableOptions;
import by.betrayal.audienceservice.utils.pagination.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/audiences/types")
@RequiredArgsConstructor
public class AudienceTypeController {

    private final AudienceTypeService typeService;

    @GetMapping
    public ResponseEntity<List<AudienceTypeFullDto>> findAll(
            @RequestParam(name = Pagination.PARAM_LIMIT, required = false) Integer limit,
            @RequestParam(name = Pagination.PARAM_PAGE, required = false) Integer page
    ) {
        if (limit == null || page == null) {
            var types = typeService.findAll();
            return new ResponseEntity<>(types, HttpStatus.OK);
        }

        var options = new PageableOptions(limit, page - 1);
        var types = typeService.findAll(options);

        return ResponseEntity.ok()
                .header(Pagination.HEADER_TOTAL_COUNT, String.valueOf(types.totalCount()))
                .body(types.items());
    }

    @GetMapping("{id}")
    public ResponseEntity<AudienceTypeFullDto> findById(@PathVariable Short id) {
        var institution = typeService.findById(id);

        return new ResponseEntity<>(institution, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AudienceTypeFullDto> create(@Valid @RequestBody AudienceTypeCreateDto dto) {
        var institution = typeService.create(dto);

        return new ResponseEntity<>(institution, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AudienceTypeFullDto> update(@Valid @RequestBody AudienceTypeUpdateDto dto) {
        var institution = typeService.update(dto);

        return new ResponseEntity<>(institution, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AudienceTypeFullDto> delete(@PathVariable Short id) {
        var institution = typeService.delete(id);

        return new ResponseEntity<>(institution, HttpStatus.OK);
    }
}
