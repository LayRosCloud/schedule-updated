package by.betrayal.scheduleservice.dto.time;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@Builder
public class CreateTimeDto {

    private Time timeStart;
    private Time timeEnd;
    private Long institutionId;
}
