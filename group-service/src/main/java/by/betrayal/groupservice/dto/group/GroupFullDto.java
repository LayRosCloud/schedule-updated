package by.betrayal.groupservice.dto.group;

import by.betrayal.groupservice.dto.course.CourseFullDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupFullDto {

    private Long id;
    private String name;
    private CourseFullDto course;
}
