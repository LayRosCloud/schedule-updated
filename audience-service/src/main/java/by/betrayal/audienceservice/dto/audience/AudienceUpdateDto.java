package by.betrayal.audienceservice.dto.audience;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AudienceUpdateDto {

    @Min(value = 1, message = "id less 1")
    private Long id;

    @Size(min = 1, max = 20, message = "name wrong size 1 between 20 letters")
    @NotBlank(message = "blank name")
    private String name;

    @Min(value = 1, message = "corpusId less 1")
    private Long corpusId;

    @Min(value = 1, message = "typeId less 1")
    private Short typeId;
}
