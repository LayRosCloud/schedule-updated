package by.betrayal.groupservice.dto.faculty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFacultyDto {

    private String name;
    private Long corpusId;
}
