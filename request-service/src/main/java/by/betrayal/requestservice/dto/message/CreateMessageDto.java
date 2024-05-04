package by.betrayal.requestservice.dto.message;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CreateMessageDto {

    @NotBlank
    @Length(min = 1, max = 4096, message = "text not between 1 and 4096 length")
    private String text;

    @Min(value = 1, message = "participantId has min 1 value")
    @NotNull(message = "participantId is null")
    private Long participantId;
}
