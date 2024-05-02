package by.betrayal.requestservice.service.impl;

import by.betrayal.requestservice.dto.participant.CreateParticipantDto;
import by.betrayal.requestservice.dto.participant.ParticipantFullDto;
import by.betrayal.requestservice.dto.participant.UpdateParticipantDto;
import by.betrayal.requestservice.entity.MessageEntity;
import by.betrayal.requestservice.entity.ParticipantEntity;
import by.betrayal.requestservice.entity.RequestEntity;
import by.betrayal.requestservice.entity.TypeMessage;
import by.betrayal.requestservice.mapper.ParticipantMapper;
import by.betrayal.requestservice.repository.MessageRepository;
import by.betrayal.requestservice.repository.ParticipantRepository;
import by.betrayal.requestservice.repository.RequestRepository;
import by.betrayal.requestservice.service.ParticipantService;
import by.betrayal.requestservice.utils.DateUtils;
import by.betrayal.requestservice.utils.ExceptionUtils;
import by.betrayal.requestservice.utils.pageable.PageableContainer;
import by.betrayal.requestservice.utils.pageable.PageableFactory;
import by.betrayal.requestservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final RequestRepository requestRepository;
    private final MessageRepository messageRepository;
    private final ParticipantMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<ParticipantFullDto> findAllByPersonId(Long personId, PageableOptions options) {
        var pageable = PageableFactory.createPageableAsc(options);
        var participantPage = participantRepository.findAllByPersonIdAndIsHiddenNot(personId, pageable);
        var participants = mapper.mapToDto(participantPage.getContent());
        return new PageableContainer<>(participantPage.getTotalElements(), participants);
    }

    @Override
    @Transactional(readOnly = true)
    public ParticipantFullDto findById(Long id) {
        ParticipantEntity participants = findByIdParticipantOrThrowNotFoundException(id);
        return mapper.mapToDto(participants);
    }

    @Override
    @Transactional
    public ParticipantFullDto create(CreateParticipantDto dto) {
        var request = findByIdRequestOrThrowNotFoundException(dto.getRequestId());
        var participant = mapper.mapToEntity(dto);
        participant.setRequest(request);
        var result = participantRepository.save(participant);

        //TODO: make ACID request to service people and get current user
        var message = new MessageEntity();
        message.setText(String.format("`%s` пригласил `%s`", "Иванов Иван", dto.getPersonId()));
        message.setCreatedAt(DateUtils.getTicksFromUtcZone());
        message.setUpdatedAt(DateUtils.getTicksFromUtcZone());
        message.setType(TypeMessage.SYSTEM);
        message.setParticipant(result);

        messageRepository.save(message);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public ParticipantFullDto update(UpdateParticipantDto dto) {
        var participant = findByIdParticipantOrThrowNotFoundException(dto.getId());

        mapper.mapToEntity(participant, dto);
        var result = participantRepository.save(participant);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public ParticipantFullDto delete(Long id) {
        var participant = findByIdParticipantOrThrowNotFoundException(id);
        participant.setDeletedAt(DateUtils.getTicksFromUtcZone());
        var result = participantRepository.save(participant);
        return mapper.mapToDto(result);
    }

    private ParticipantEntity findByIdParticipantOrThrowNotFoundException(Long id) {
        return participantRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Participant", id)
        );
    }

    private RequestEntity findByIdRequestOrThrowNotFoundException(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Request", requestId)
        );
    }
}
