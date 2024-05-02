package by.betrayal.requestservice.dto.message;

import by.betrayal.requestservice.dto.participant.ParticipantPreviewDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessagePreviewDto {

    private Long id;

    private String text;

    private Long createdAt;

    private Long updatedAt;

    private ParticipantPreviewDto participant;
}
