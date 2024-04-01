package by.betrayal.accountservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {

    @Min(value = 1, message = "id less 1")
    private Long id;

    @Size(min = 4, max = 50, message = "username is not between 4 and 50 symbols")
    @NotBlank
    private String login;

    @Email
    @NotBlank
    private String email;

    private Boolean isBanned;

    private Boolean isActivated;

    @Min(value = 1, message = "personId less 1")
    private Long personId;
}
