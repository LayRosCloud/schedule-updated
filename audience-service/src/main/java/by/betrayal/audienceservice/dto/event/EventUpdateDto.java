package by.betrayal.audienceservice.dto.event;

import by.betrayal.audienceservice.utils.validation.MinDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EventUpdateDto {

    @Min(value = 1, message = "id less 1")
    private Long id;

    @Min(value = 1, message = "audienceId less 1")
    private Long audienceId;

    @NotBlank
    @Size(min = 1, max = 2, message = "")
    private String value;

    @MinDate(message = "date less current date")
    @NotEmpty
    private Date dateStart;

    @MinDate(message = "date less current date")
    @NotEmpty
    private Date dateEnd;
}
