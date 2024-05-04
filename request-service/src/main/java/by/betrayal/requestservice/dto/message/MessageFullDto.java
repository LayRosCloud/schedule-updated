package by.betrayal.requestservice.dto.message;

import by.betrayal.requestservice.dto.participant.ParticipantFullDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageFullDto {

    private Long id;

    private String text;

    private Long createdAt;

    private Long updatedAt;

    private ParticipantFullDto participant;
}
