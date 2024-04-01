package by.betrayal.accountservice.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {

    @Size(min = 4, max = 50, message = "username is not between 4 and 50 symbols")
    @NotBlank(message = "username is blank")
    private String username;

    @Size(min = 4, max = 30, message = "password is not between 4 and 30 symbols")
    @NotBlank(message = "password is blank")
    private String password;
}
