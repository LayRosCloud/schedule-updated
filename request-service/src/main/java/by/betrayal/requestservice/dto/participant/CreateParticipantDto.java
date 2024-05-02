package by.betrayal.requestservice.dto.participant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateParticipantDto {

    private Long personId;
    private Long requestId;
}
