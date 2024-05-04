package by.betrayal.scheduleservice.dto.subgroup;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CreateSubgroupDto {

    @Min(value = 1, message = "groupId has min 1 value")
    @NotNull(message = "groupId is null")
    private Long groupId;

    @Length(min = 1, max = 20, message = "name has min 1 or max 20 length")
    @NotBlank(message = "name is null")
    private String name;
}
