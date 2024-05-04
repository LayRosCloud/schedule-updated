package by.betrayal.groupservice.mapper;

import by.betrayal.groupservice.dto.course.CourseFullDto;
import by.betrayal.groupservice.dto.course.CreateCourseDto;
import by.betrayal.groupservice.dto.course.UpdateCourseDto;
import by.betrayal.groupservice.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { FacultyMapper.class })
public interface CourseMapper {
    CourseFullDto mapToDto(CourseEntity course);
    List<CourseFullDto> mapToDto(List<CourseEntity> course);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    CourseEntity mapToEntity(CreateCourseDto dto);

    default void mapToEntity(CourseEntity course, UpdateCourseDto dto) {
        course.setName(dto.getName());
    }
}
