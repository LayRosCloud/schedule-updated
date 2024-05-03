package by.betrayal.groupservice.dto.faculty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFacultyDto {

    private Long id;
    private String name;
    private Long corpusId;
}
