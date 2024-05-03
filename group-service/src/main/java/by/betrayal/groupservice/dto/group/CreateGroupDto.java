package by.betrayal.groupservice.dto.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroupDto {

    private String name;
    private Long courseId;
}
