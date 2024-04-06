package by.betrayal.audienceservice.dto.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EventCreateDto {

    @Min(value = 1, message = "audienceId less 1")
    private Long audienceId;

    @NotBlank
    @Size(min = 1, max = 50, message = "")
    private String value;

    private Date dateStart;

    private Date dateEnd;
}
