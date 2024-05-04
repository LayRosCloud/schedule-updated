package by.betrayal.scheduleservice.dto.subject;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CreateSubjectDto {

    @NotBlank(message = "name is blank")
    @Length(min = 1, max = 15)
    private String name;

    @Max(value = 40, message = "longName must be min 40 length")
    private String longName;

    @Min(value = 1)
    @NotNull(message = "institutionId is null")
    private Long institutionId;
}
