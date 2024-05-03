package by.betrayal.groupservice.dto.event;

import by.betrayal.groupservice.dto.group.GroupFullDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventFullDto {

    private Long id;
    private String value;
    private Long dateStart;
    private Long dateEnd;
    private GroupFullDto group;
}
