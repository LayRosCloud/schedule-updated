package by.betrayal.audienceservice.dto.corpus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CorpusCreateDto {

    @Size(min = 0, max = 20, message = "address not between 1 and 20 symbols")
    private String name;

    @Size(min = 0, max = 200, message = "address not between 6 and 200 symbols")
    private String address;

    @Min(value = 1, message = "institutionId less 1")
    private Long institutionId;
}
