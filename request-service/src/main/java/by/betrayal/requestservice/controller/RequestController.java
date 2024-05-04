package by.betrayal.requestservice.controller;

import by.betrayal.requestservice.dto.request.CreateRequestDto;
import by.betrayal.requestservice.dto.request.RequestFullDto;
import by.betrayal.requestservice.dto.request.UpdateRequestDto;
import by.betrayal.requestservice.service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RequestController {
    public static final String ENDPOINT = "v1/requests";
    public static final String ENDPOINT_BY_ID = "v1/requests/{id}";
    private final RequestService service;

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<RequestFullDto> findById(@PathVariable Long id) {
        var request = service.findById(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<RequestFullDto> create(@Valid @RequestBody CreateRequestDto dto) {
        var request = service.create(dto);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<RequestFullDto> update(@Valid @RequestBody UpdateRequestDto dto) {
        var request = service.update(dto);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<RequestFullDto> delete(@PathVariable Long id) {
        var request = service.delete(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
