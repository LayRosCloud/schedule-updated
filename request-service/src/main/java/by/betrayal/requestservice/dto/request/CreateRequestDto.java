package by.betrayal.requestservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateRequestDto {

    private String theme;
    private List<Long> personIds;
}
