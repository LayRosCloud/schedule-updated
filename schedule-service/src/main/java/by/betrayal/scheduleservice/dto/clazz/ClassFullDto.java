package by.betrayal.scheduleservice.dto.clazz;

import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.teachersubject.TeacherSubjectFullDto;
import by.betrayal.scheduleservice.dto.time.TimeFullDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClassFullDto {

    private Long id;
    private Date dateStart;
    private Date dateEnd;
    private Long audienceId;
    private TeacherSubjectFullDto teacherSubjectId;
    private SubgroupFullDto subgroup;
    private TimeFullDto time;
    private TypeClassFullDto type;
}
