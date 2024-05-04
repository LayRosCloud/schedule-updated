package by.betrayal.requestservice.service.impl;

import by.betrayal.requestservice.dto.request.CreateRequestDto;
import by.betrayal.requestservice.dto.request.RequestFullDto;
import by.betrayal.requestservice.dto.request.UpdateRequestDto;
import by.betrayal.requestservice.entity.ParticipantEntity;
import by.betrayal.requestservice.entity.RequestEntity;
import by.betrayal.requestservice.mapper.RequestMapper;
import by.betrayal.requestservice.repository.ParticipantRepository;
import by.betrayal.requestservice.repository.RequestRepository;
import by.betrayal.requestservice.service.RequestService;
import by.betrayal.requestservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final ParticipantRepository participantRepository;
    private final RequestMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public RequestFullDto findById(Long id) {
        RequestEntity request = findByIdRequestOrThrowNotFoundException(id);
        return mapper.mapToDto(request);
    }

    @Override
    @Transactional
    public RequestFullDto create(CreateRequestDto dto) {
        var request = mapper.mapToEntity(dto);
        var result = requestRepository.save(request);

        var participants = new ArrayList<ParticipantEntity>();

        for (var id: dto.getPersonIds()) {
            var participant = new ParticipantEntity();
            participant.setRequest(result);
            participant.setPersonId(id);
            participants.add(participant);
        }
        participantRepository.saveAll(participants);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public RequestFullDto update(UpdateRequestDto dto) {
        var request = findByIdRequestOrThrowNotFoundException(dto.getId());
        mapper.mapToEntity(request, dto);
        var result = requestRepository.save(request);
        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public RequestFullDto delete(Long id) {
        var request = findByIdRequestOrThrowNotFoundException(id);
        requestRepository.delete(request);
        return mapper.mapToDto(request);
    }

    private RequestEntity findByIdRequestOrThrowNotFoundException(Long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Request", id)
        );
    }
}
