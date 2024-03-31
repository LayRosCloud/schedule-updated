package by.betrayal.accountservice.service.impl;

import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.entity.TokenEntity;
import by.betrayal.accountservice.entity.UserEntity;
import by.betrayal.accountservice.repository.PermissionRepository;
import by.betrayal.accountservice.repository.TokenRepository;
import by.betrayal.accountservice.service.TokenService;
import by.betrayal.accountservice.utils.ThrowableUtils;
import by.betrayal.accountservice.utils.token.AccessTokenUtils;
import by.betrayal.accountservice.utils.token.RefreshTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    private final AccessTokenUtils accessTokenUtils;
    private final RefreshTokenUtils refreshTokenUtils;


    @Override
    @Transactional
    public JwtFullDto generateTokens(UserEntity user) {
        String accessToken = accessTokenUtils.generateToken(user.getId());
        String refreshToken = refreshTokenUtils.generateToken(user);

        return JwtFullDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public JwtFullDto refresh(String token) {
        var refreshToken = repository.findByValue(token).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Token is not found")
        );

        Long userId = refreshTokenUtils.extractId(token);

        JwtFullDto accessDto = JwtFullDto.builder()
                .accessToken(accessTokenUtils.generateToken(userId))
                .refreshToken(refreshToken.getValue())
                .build();

        return accessDto;
    }

    @Override
    @Transactional
    public void delete(String token) {
        var refreshToken = repository.findByValue(token).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("token is not found")
        );

        repository.delete(refreshToken);
    }

    @Override
    public boolean expiredToken(String token) {
        final var expiredTicks = refreshTokenUtils.extractExpiredTicks(token);
        final var expiredDate = new Date(expiredTicks * 1000);
        final var now = new Date();

        return now.after(expiredDate);
    }
}
