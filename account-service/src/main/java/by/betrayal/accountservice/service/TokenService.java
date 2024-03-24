package by.betrayal.accountservice.service;

import by.betrayal.accountservice.dto.token.JwtAccessDto;
import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.dto.token.RefreshAccessTokenDto;
import by.betrayal.accountservice.entity.PermissionEntity;
import by.betrayal.accountservice.entity.UserEntity;

import java.util.List;

public interface TokenService {
    JwtFullDto generateTokens(UserEntity user);
    JwtFullDto refresh(String token);
    Long extractId(String token);
    boolean expiredToken(String token);
    void delete(String token);
}
