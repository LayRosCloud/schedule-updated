package by.betrayal.scheduleservice.dto.teachersubject;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTeacherSubjectDto {

    private Long teacherId;
    private Long subjectId;
}
