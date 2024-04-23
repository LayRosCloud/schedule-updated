package by.betrayal.scheduleservice.dto.subgroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateSubgroupDto {

    private Long id;
    private Long groupId;
    private String name;
}
