package by.betrayal.scheduleservice.dto.teachersubject;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTeacherSubjectDto {

    @Min(value = 1, message = "id has min 1 value")
    @NotNull(message = "id is null")
    private Long id;

    @Min(value = 1, message = "teacherId has min 1 value")
    @NotNull(message = "teacherId is null")
    private Long teacherId;

    @Min(value = 1, message = "subjectId has min 1 value")
    @NotNull(message = "subjectId is null")
    private Long subjectId;
}
