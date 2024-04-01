package by.betrayal.audienceservice.dto.audiencetype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AudienceTypeCreateDto {

    @Size(min = 1, max = 30, message = "name not between 1 and 30 letters")
    @NotBlank(message = "blank name")
    private String name;
}
