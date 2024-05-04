package by.betrayal.requestservice.dto.participant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateParticipantDto {

    @NotNull(message = "id is null")
    @Min(value = 1, message = "id is min 1 value")
    private Long id;

    @NotNull(message = "isHidden is null")
    private Boolean isHidden;
}
