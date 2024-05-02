package by.betrayal.requestservice.service.impl;

import by.betrayal.requestservice.dto.message.CreateMessageDto;
import by.betrayal.requestservice.dto.message.MessageFullDto;
import by.betrayal.requestservice.dto.message.MessagePreviewDto;
import by.betrayal.requestservice.dto.message.UpdateMessageDto;
import by.betrayal.requestservice.entity.MessageEntity;
import by.betrayal.requestservice.entity.ParticipantEntity;
import by.betrayal.requestservice.entity.TypeMessage;
import by.betrayal.requestservice.mapper.MessageMapper;
import by.betrayal.requestservice.repository.MessageRepository;
import by.betrayal.requestservice.repository.ParticipantRepository;
import by.betrayal.requestservice.service.MessageService;
import by.betrayal.requestservice.utils.DateUtils;
import by.betrayal.requestservice.utils.ExceptionUtils;
import by.betrayal.requestservice.utils.pageable.PageableContainer;
import by.betrayal.requestservice.utils.pageable.PageableFactory;
import by.betrayal.requestservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;
    private final MessageMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<MessagePreviewDto> findAllByRequestId(Long requestId, PageableOptions options) {
        var pageable = PageableFactory.createPageableDesc(options);
        //TODO: SQL query on delete person messages
        var messagePage = messageRepository.findAllByRequestId(requestId, pageable);
        var messages = mapper.mapToDto(messagePage.getContent());
        return new PageableContainer<>(messagePage.getTotalElements(), messages);
    }

    @Override
    @Transactional(readOnly = true)
    public MessageFullDto findById(Long id) {
        var message = findByIdMessageOrThrowNotFoundException(id);

        return mapper.mapToFullDto(message);
    }
    @Override
    @Transactional
    public MessageFullDto create(CreateMessageDto dto) {
        var participant = findByIdParticipantOrThrowNotFoundException(dto.getParticipantId());
        var message = mapper.mapToEntity(dto);

        message.setParticipant(participant);
        var nowEpochSecond = DateUtils.getTicksFromUtcZone();
        message.setCreatedAt(nowEpochSecond);
        message.setUpdatedAt(nowEpochSecond);
        message.setType(TypeMessage.USER);

        var result = messageRepository.save(message);
        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public List<MessageFullDto> create(List<CreateMessageDto> dtos) {
        if (dtos.size() == 0) {
            throw ExceptionUtils.getBadRequestException("List with messages is empty");
        }

        var participant = findByIdParticipantOrThrowNotFoundException(dtos.get(0).getParticipantId());
        var listMessages = new ArrayList<MessageEntity>(dtos.size());
        var nowEpochSecond = DateUtils.getTicksFromUtcZone();

        for (var dto: dtos) {
            var message = mapper.mapToEntity(dto);
            message.setParticipant(participant);
            message.setCreatedAt(nowEpochSecond);
            message.setUpdatedAt(nowEpochSecond);
            message.setType(TypeMessage.USER);
            listMessages.add(message);
        }

        var result = messageRepository.saveAll(listMessages);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public MessageFullDto update(UpdateMessageDto dto) {
        var message = findByIdMessageOrThrowNotFoundException(dto.getId());
        mapper.mapToEntity(message, dto);
        var ticksFromUtcZone = DateUtils.getTicksFromUtcZone();
        message.setUpdatedAt(ticksFromUtcZone);
        var result = messageRepository.save(message);
        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public MessageFullDto delete(Long id) {
        var message = findByIdMessageOrThrowNotFoundException(id);
        messageRepository.delete(message);
        return mapper.mapToFullDto(message);
    }

    private MessageEntity findByIdMessageOrThrowNotFoundException(Long id) {
        return messageRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Message", id)
        );
    }

    private ParticipantEntity findByIdParticipantOrThrowNotFoundException(Long participantId) {
        return participantRepository.findById(participantId).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Participant", participantId)
        );
    }

}
