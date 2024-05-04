package by.betrayal.groupservice.dto.course;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateCourseDto {

    @NotNull(message = "id is null")
    @Min(value = 1, message = "id be min 1 length")
    private Long id;

    @NotBlank(message = "name is blank")
    @Length(min = 1, max = 10, message = "name is not between 1 and 10 length")
    private String name;

    @NotNull(message = "facultyId is null")
    @Min(value = 1, message = "facultyId be min 1 length")
    private Long facultyId;
}
