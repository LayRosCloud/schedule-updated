package by.betrayal.groupservice.dto.course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCourseDto {

    private String name;
    private Long facultyId;
}
