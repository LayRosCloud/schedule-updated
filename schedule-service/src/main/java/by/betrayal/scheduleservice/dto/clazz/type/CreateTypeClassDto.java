package by.betrayal.scheduleservice.dto.clazz.type;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTypeClassDto {

    private String name;
    private Long institutionId;
}
