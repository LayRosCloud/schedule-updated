package by.betrayal.scheduleservice.dto.clazz;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UpdateClassDto {

    @Min(value = 1, message = "id has min 1 value")
    private Long id;

    @NotNull(message = "dateStart can't be null")
    private Date dateStart;

    @NotNull(message = "dateEnd can't be null")
    private Date dateEnd;

    @Min(value = 1, message = "audienceId has min 1 value")
    @NotNull(message = "audienceId is null")
    private Long audienceId;

    @Min(value = 1, message = "teacherSubjectId has min 1 value")
    @NotNull(message = "teacherSubjectId is null")
    private Long teacherSubjectId;

    @Min(value = 1, message = "subgroupId has min 1 value")
    @NotNull(message = "subgroupId is null")
    private Long subgroupId;

    @Min(value = 1, message = "timeId has min 1 value")
    @NotNull(message = "timeId is null")
    private Long timeId;

    @Min(value = 1, message = "typeId has min 1 value")
    @NotNull(message = "typeId is null")
    private Long typeId;
}
