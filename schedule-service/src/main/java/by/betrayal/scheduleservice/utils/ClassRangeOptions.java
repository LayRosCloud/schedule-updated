package by.betrayal.scheduleservice.utils;

import by.betrayal.scheduleservice.dto.clazz.CreateClassDto;
import by.betrayal.scheduleservice.entity.*;
import lombok.Builder;
import lombok.Data;

import java.util.Hashtable;
import java.util.List;

@Data
@Builder
public class ClassRangeOptions {
    private Hashtable<Long, SubgroupEntity> subgroups;
    private Hashtable<Long, TeacherSubjectEntity> teacherSubjects;
    private Hashtable<Long, TimeEntity> times;
    private Hashtable<Long, TypeClassEntity> types;
    private List<CreateClassDto> dtos;

}
