package by.betrayal.requestservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
public class CreateRequestDto {

    @NotBlank(message = "theme is blank")
    @Length(min = 6, max = 255, message = "theme is not 6 between 255 length")
    private String theme;

    @NotEmpty(message = "personIds is empty")
    private List<Long> personIds;
}
