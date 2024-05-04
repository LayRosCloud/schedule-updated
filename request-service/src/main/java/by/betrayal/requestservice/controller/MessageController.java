package by.betrayal.requestservice.controller;

import by.betrayal.requestservice.dto.message.CreateMessageDto;
import by.betrayal.requestservice.dto.message.MessageFullDto;
import by.betrayal.requestservice.dto.message.MessagePreviewDto;
import by.betrayal.requestservice.dto.message.UpdateMessageDto;
import by.betrayal.requestservice.service.MessageService;
import by.betrayal.requestservice.utils.pageable.PageableContainer;
import by.betrayal.requestservice.utils.pageable.PageableOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    public static final String ENDPOINT_FIND_ALL_BY_REQUEST_ID = "v1/requests/{requestId}/messages";
    public static final String ENDPOINT_BY_ID = "v1/messages/{id}";
    public static final String ENDPOINT = "v1/messages";
    public static final String ENDPOINT_CREATE_RANGE = "v1/messages/range";

    private final MessageService service;

    @GetMapping(ENDPOINT_FIND_ALL_BY_REQUEST_ID)
    public ResponseEntity<List<MessagePreviewDto>> findAllByRequestId(
            @PathVariable Long requestId,
            @RequestParam(value = PageableContainer.PARAM_PAGE) Integer page,
            @RequestParam(value = PageableContainer.PARAM_LIMIT) Integer limit
    ) {
        var pageable = new PageableOptions(limit, page);
        var messagePage = service.findAllByRequestId(requestId, pageable);
        return ResponseEntity.ok()
                .header(PageableContainer.HEADER_TOTAL_COUNT, messagePage.totalCount().toString())
                .body(messagePage.items());
    }

    @GetMapping(ENDPOINT_BY_ID)
    public ResponseEntity<MessageFullDto> findById(@PathVariable Long id) {
        var message = service.findById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<MessageFullDto> create(@Valid @RequestBody CreateMessageDto dto) {
        var message = service.create(dto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping(ENDPOINT_CREATE_RANGE)
    public ResponseEntity<List<MessageFullDto>> create(@Valid @RequestBody List<CreateMessageDto> dtos) {
        var messages = service.create(dtos);
        return new ResponseEntity<>(messages, HttpStatus.CREATED);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<MessageFullDto> update(@Valid @RequestBody UpdateMessageDto dto) {
        var message = service.update(dto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_BY_ID)
    public ResponseEntity<MessageFullDto> delete(@PathVariable Long id) {
        var message = service.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
