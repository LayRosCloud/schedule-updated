package by.betrayal.personalservice.dto.person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonFullDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private Long institutionId;

    private String photo;
}
