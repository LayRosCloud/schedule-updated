package by.betrayal.personalservice.dto.person;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PersonCreateDto {

    @Size(min = 1, max = 50, message = "lastName not between 1 and 50 symbols")
    @NotBlank(message = "lastName is blank")
    private String lastName;

    @Size(min = 2, max = 50, message = "firstName not between 2 and 50 symbols")
    @NotBlank(message = "firstName is blank")
    private String firstName;

    private String patronymic;

    private MultipartFile photo;

    @Min(value = 1, message = "institutionId less 1")
    private Long institutionId;
}
