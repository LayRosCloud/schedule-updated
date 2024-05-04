package by.betrayal.scheduleservice.dto.time;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@Builder
public class CreateTimeDto {

    @NotNull(message = "timeEnd is null")
    private Time timeStart;

    @NotNull(message = "timeEnd is null")
    private Time timeEnd;

    @Min(value = 1, message = "institutionId has min 1 value")
    @NotNull(message = "institutionId is null")
    private Long institutionId;
}
