package by.betrayal.audienceservice.dto.corpus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorpusUpdateDto {

    @Min(value = 1, message = "id less 1")
    private Long id;

    @Size(min = 0, max = 20, message = "name not between 0 and 20 symbols")
    private String name;

    @Size(min = 0, max = 200, message = "address not between 0 and 200 symbols")
    private String address;

    @Min(value = 1, message = "institutionId less 1")
    private Long institutionId;
}
