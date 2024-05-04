package by.betrayal.scheduleservice.mapper;

import by.betrayal.scheduleservice.dto.teachersubject.CreateTeacherSubjectDto;
import by.betrayal.scheduleservice.dto.teachersubject.TeacherSubjectFullDto;
import by.betrayal.scheduleservice.dto.teachersubject.UpdateTeacherSubjectDto;
import by.betrayal.scheduleservice.entity.TeacherSubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherSubjectMapper {

    TeacherSubjectFullDto mapToDto(TeacherSubjectEntity subject);
    List<TeacherSubjectFullDto> mapToDto(List<TeacherSubjectEntity> subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    TeacherSubjectEntity mapToEntity(CreateTeacherSubjectDto dto);

    default void mapToEntity(TeacherSubjectEntity subject, UpdateTeacherSubjectDto dto) {
        subject.setTeacherId(dto.getTeacherId());
    }
}
