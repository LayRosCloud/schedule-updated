package by.betrayal.accountservice.dto.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtFullDto {

    private String accessToken;

    private String refreshToken;
}
