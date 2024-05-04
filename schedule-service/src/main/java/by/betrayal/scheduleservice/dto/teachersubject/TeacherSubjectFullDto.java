package by.betrayal.scheduleservice.dto.teachersubject;

import by.betrayal.scheduleservice.dto.subject.SubjectFullDto;
import lombok.Data;

@Data
public class TeacherSubjectFullDto {

    private Long id;
    private Long teacherId;
    private SubjectFullDto subjectId;
}
