package by.betrayal.groupservice.dto.course;

import by.betrayal.groupservice.dto.faculty.FacultyFullDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseFullDto {

    private Long id;
    private String name;
    private FacultyFullDto faculty;
}
