package by.betrayal.audienceservice.dto.institution;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstitutionUpdateDto {

    @Min(value = 1, message = "id less 1")
    private Long id;

    @Size(min = 3, max = 100, message = "name not between 1 and 100 symbols")
    @NotBlank(message = "blank name")
    private String name;
}
