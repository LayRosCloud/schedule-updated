package by.betrayal.groupservice.dto.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateGroupDto {

    private Long id;
    private String name;
    private Long courseId;
}
