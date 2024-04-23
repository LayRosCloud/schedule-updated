package by.betrayal.scheduleservice.dto.subject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSubjectDto {

    private String name;
    private String longName;
    private Long institutionId;
}
