package by.betrayal.requestservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRequestDto {

    private Long id;
    private String theme;
    private Boolean approved;
}
