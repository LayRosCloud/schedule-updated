package by.betrayal.audienceservice.dto.institution;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionCreateDto {
    @Size(min = 3, max = 100, message = "name not between 1 and 100 symbols")
    @NotBlank(message = "blank name")
    private String name;
}
