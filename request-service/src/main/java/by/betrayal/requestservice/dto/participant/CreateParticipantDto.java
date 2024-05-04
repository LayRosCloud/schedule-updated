package by.betrayal.requestservice.dto.participant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateParticipantDto {

    @Min(value = 1, message = "personId has min 1 value")
    @NotNull(message = "participantId is null")
    private Long personId;

    @Min(value = 1, message = "requestId has min 1 value")
    @NotNull(message = "participantId is null")
    private Long requestId;
}
