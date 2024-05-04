package by.betrayal.scheduleservice.dto.subject;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateSubjectDto {

    @Min(value = 1, message = "id has min 1 value")
    private Long id;

    @Length(min = 2, max = 15, message = "name has min 2 or max 15 length")
    @NotBlank(message = "name is blank")
    private String name;

    @Length(min = 3, max = 40, message = "longName must be min 3 and max 40 length")
    private String longName;

    @Min(value = 1, message = "institutionId has min 1 value")
    @NotNull(message = "institutionId is null")
    private Long institutionId;
}
