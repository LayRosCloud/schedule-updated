package by.betrayal.requestservice.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateMessageDto {

    private Long id;

    private String text;
}
