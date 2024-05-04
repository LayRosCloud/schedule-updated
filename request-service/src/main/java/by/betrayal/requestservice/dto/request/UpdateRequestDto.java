package by.betrayal.requestservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateRequestDto {

    @Min(value = 1, message = "id is min 1 value")
    @NotNull(message = "id is null")
    private Long id;

    @NotBlank(message = "theme is blank")
    @Length(min = 6, max = 255, message = "theme is not 6 between 255 length")
    private String theme;

    private Boolean approved;
}
