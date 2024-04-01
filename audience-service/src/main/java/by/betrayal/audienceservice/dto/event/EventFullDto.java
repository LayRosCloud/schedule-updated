package by.betrayal.audienceservice.dto.event;

import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import lombok.Data;

import java.util.Date;

@Data
public class EventFullDto {

    private Long id;
    private AudienceFullDto audience;
    private String value;
    private Date dateStart;
    private Date dateEnd;
}
