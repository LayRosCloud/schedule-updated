package by.betrayal.accountservice.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserCreateDto {

    @Size(min = 4, max = 30)
    private String login;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotEmpty
    private Set<String> scopes;

    @Digits(message = "PersonId was not a number", integer = Integer.MAX_VALUE, fraction = 0)
    private Long personId;
}
