package by.betrayal.requestservice.mapper;

import by.betrayal.requestservice.dto.participant.CreateParticipantDto;
import by.betrayal.requestservice.dto.participant.ParticipantFullDto;
import by.betrayal.requestservice.dto.participant.UpdateParticipantDto;
import by.betrayal.requestservice.entity.ParticipantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    ParticipantFullDto mapToDto(ParticipantEntity participant);
    List<ParticipantFullDto> mapToDto(List<ParticipantEntity> participants);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "request", ignore = true)
    ParticipantEntity mapToEntity(CreateParticipantDto dto);

    default void mapToEntity(ParticipantEntity participant, UpdateParticipantDto dto) {
        participant.setPersonId(dto.getPersonId());
    }
}
