package by.betrayal.scheduleservice.dto.clazz.type;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateTypeClassDto {

    @Min(value = 1, message = "id has min 1 value")
    private Long id;

    @Length(min = 1, max = 30, message = "name has min 1 or max 30 length")
    @NotBlank(message = "name is blank")
    private String name;

    @Min(value = 1, message = "institutionId has min 1 value")
    @NotNull(message = "institutionId is null")
    private Long institutionId;
}
