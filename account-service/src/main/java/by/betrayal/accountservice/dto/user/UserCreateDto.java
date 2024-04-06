package by.betrayal.accountservice.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserCreateDto {

    @Size(min = 4, max = 30)
    @NotBlank(message = "login is blank")
    private String login;

    @Email
    @NotBlank(message = "email is blank")
    private String email;

    @Size(min = 4, max = 30)
    @NotBlank(message = "password is blank")
    private String password;

    @NotEmpty
    private Set<String> scopes;

    @Min(value = 1, message = "personId less 1")
    private Long personId;
}
