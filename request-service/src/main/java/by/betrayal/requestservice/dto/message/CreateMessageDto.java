package by.betrayal.requestservice.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMessageDto {

    private String text;

    private Long participantId;
}
