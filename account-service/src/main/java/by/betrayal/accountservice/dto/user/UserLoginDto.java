package by.betrayal.accountservice.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {

    @Size(min = 4, max = 50)
    private String username;

    @NotBlank
    private String password;
}
