package by.betrayal.requestservice.service;


import by.betrayal.requestservice.dto.participant.CreateParticipantDto;
import by.betrayal.requestservice.dto.participant.ParticipantFullDto;
import by.betrayal.requestservice.dto.participant.UpdateParticipantDto;
import by.betrayal.requestservice.utils.pageable.PageableContainer;
import by.betrayal.requestservice.utils.pageable.PageableOptions;

public interface ParticipantService {

    PageableContainer<ParticipantFullDto> findAllByPersonId(Long personId, PageableOptions options);
    ParticipantFullDto findById(Long id);
    ParticipantFullDto create(CreateParticipantDto dto);
    ParticipantFullDto delete(Long id);
}
