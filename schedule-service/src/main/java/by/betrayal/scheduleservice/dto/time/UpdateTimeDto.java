package by.betrayal.scheduleservice.dto.time;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@Builder
public class UpdateTimeDto {

    @Min(value = 1, message = "id has min 1 value")
    private Long id;

    private Time timeStart;

    private Time timeEnd;

    @Min(value = 1, message = "institutionId has min 1 value")
    private Long institutionId;
}
