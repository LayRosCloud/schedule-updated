package by.betrayal.requestservice.dto.message;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateMessageDto {

    @Min(value = 1, message = "id has min 1 value")
    @NotNull(message = "id is null")
    private Long id;

    @Length(min = 1, max = 4096, message = "text not between 1 and 4096 length")
    private String text;
}
