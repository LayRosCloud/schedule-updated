package by.betrayal.groupservice.dto.group;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CreateGroupDto {

    @NotBlank
    @Length(min = 1, max = 20, message = "name is not between 1 and 20 length")
    @NotNull(message = "name is null")
    private String name;

    @NotNull(message = "courseId is null")
    @Min(value = 1, message = "courseId is min 1 value")
    private Long courseId;
}
