package by.betrayal.requestservice.service;

import by.betrayal.requestservice.dto.message.CreateMessageDto;
import by.betrayal.requestservice.dto.message.MessageFullDto;
import by.betrayal.requestservice.dto.message.MessagePreviewDto;
import by.betrayal.requestservice.dto.message.UpdateMessageDto;
import by.betrayal.requestservice.utils.pageable.PageableContainer;
import by.betrayal.requestservice.utils.pageable.PageableOptions;

import java.util.List;

public interface MessageService {

    PageableContainer<MessagePreviewDto> findAllByRequestId(Long requestId, PageableOptions options);
    MessageFullDto findById(Long id);
    MessageFullDto create(CreateMessageDto dto);
    List<MessageFullDto> create(List<CreateMessageDto> dtos);
    MessageFullDto update(UpdateMessageDto dto);
    MessageFullDto delete(Long id);
}
