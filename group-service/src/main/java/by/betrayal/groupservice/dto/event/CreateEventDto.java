package by.betrayal.groupservice.dto.event;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateEventDto {

    private String value;
    private Date dateStart;
    private Date dateEnd;
    private Long groupId;
}
