package by.betrayal.accountservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {

    private Long id;

    private String login;

    private String email;

    private Boolean isBanned;

    private Boolean isActivated;

    private Long personId;
}
