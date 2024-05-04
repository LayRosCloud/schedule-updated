package by.betrayal.requestservice.dto.participant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantPreviewDto {

    private Long id;
    private Long personId;
    private Long requestId;
}
