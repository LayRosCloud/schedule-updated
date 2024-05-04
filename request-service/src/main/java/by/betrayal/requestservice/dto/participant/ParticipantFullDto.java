package by.betrayal.requestservice.dto.participant;

import by.betrayal.requestservice.dto.request.RequestFullDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantFullDto {

    private Long id;
    private Long personId;
    private RequestFullDto request;
}
