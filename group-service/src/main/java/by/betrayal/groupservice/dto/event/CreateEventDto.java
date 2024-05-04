package by.betrayal.groupservice.dto.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Builder
public class CreateEventDto {

    @NotBlank
    @Length(min = 3, max = 255, message = "value is not between 3 and 255 length")
    @NotNull(message = "value is null")
    private String value;

    @NotNull(message = "dateStart is null")
    private Date dateStart;

    @NotNull(message = "dateStart is null")
    private Date dateEnd;

    @NotNull(message = "groupId is null")
    @Min(value = 1, message = "groupId is min 1 value")
    private Long groupId;
}
