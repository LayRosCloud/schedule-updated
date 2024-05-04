package by.betrayal.groupservice.dto.faculty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateFacultyDto {

    @NotNull(message = "id is null")
    @Min(value = 1, message = "id is min 1 value")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 30, message = "name is not between 2 and 30 length")
    @NotNull(message = "name is null")
    private String name;

    @NotNull(message = "corpusId is null")
    @Min(value = 1, message = "corpusId is min 1 value")
    private Long corpusId;
}
