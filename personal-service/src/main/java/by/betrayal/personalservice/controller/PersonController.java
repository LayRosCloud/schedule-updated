package by.betrayal.personalservice.controller;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.dto.person.PersonUpdateDto;
import by.betrayal.personalservice.service.PersonService;
import by.betrayal.personalservice.utils.pageable.PageOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/people")
public class PersonController {

    private final PersonService service;
    private static final String ENDPOINT_ID = "{id}";

    @GetMapping
    public ResponseEntity<List<PersonFullDto>> findAll(
            @RequestParam(name = "_limit", required = false) Integer limit,
            @RequestParam(name = "_page", required = false) Integer page
    ) {
        if (limit == null || page == null) {
            var list = service.findAll();

            return new ResponseEntity<>(list, HttpStatus.OK);
        }

        var options = new PageOptions(limit, page - 1);
        var container = service.findAll(options);
        return ResponseEntity.ok()
                .header("x-total-count", container.totalCount().toString())
                .body(container.items());
    }

    @GetMapping(ENDPOINT_ID)
    public ResponseEntity<PersonFullDto> findById(@PathVariable Long id) {
        var item = service.findById(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonFullDto> create(@Valid @ModelAttribute PersonCreateDto dto) {
        var item = service.create(dto);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PersonFullDto> update(@Valid @ModelAttribute PersonUpdateDto dto) {
        var item = service.update(dto);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_ID)
    public ResponseEntity<PersonFullDto> delete(@PathVariable Long id) {
        var item = service.delete(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
