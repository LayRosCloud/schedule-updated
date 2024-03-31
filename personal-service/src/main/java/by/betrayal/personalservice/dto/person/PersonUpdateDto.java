package by.betrayal.personalservice.dto.person;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PersonUpdateDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private MultipartFile photo;

    private Long institutionId;
}
