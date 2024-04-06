package by.betrayal.accountservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFullDto {

    private Long id;

    private String login;

    private String email;

    private Boolean isBanned;

    private Boolean isActivated;

    private Long createdAt;

    private Long personId;
}
