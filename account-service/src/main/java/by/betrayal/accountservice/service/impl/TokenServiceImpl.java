package by.betrayal.accountservice.service.impl;

import by.betrayal.accountservice.dto.token.JwtAccessDto;
import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.dto.token.RefreshAccessTokenDto;
import by.betrayal.accountservice.entity.PermissionEntity;
import by.betrayal.accountservice.entity.TokenEntity;
import by.betrayal.accountservice.entity.UserEntity;
import by.betrayal.accountservice.exception.BadRequestException;
import by.betrayal.accountservice.exception.NotFoundException;
import by.betrayal.accountservice.repository.PermissionRepository;
import by.betrayal.accountservice.repository.TokenRepository;
import by.betrayal.accountservice.repository.UserRepository;
import by.betrayal.accountservice.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String ID_KEY = "id";
    private static final String SCOPE_KEY = "scopes";
    private static final String EXPIRED_KEY = "exp";

    private final TokenRepository repository;
    private final PermissionRepository permissionRepository;
    @Override
    @Transactional
    public JwtFullDto generateTokens(UserEntity user) {
        String accessToken = generateAccessToken(user.getId());
        String refreshToken = generateRefreshToken(user);

        return JwtFullDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public JwtFullDto refresh(String token) {
        var refreshToken = repository.findByValue(token).orElseThrow(() ->
                new NotFoundException()
        );

        Long userId = extractId(token);

        JwtFullDto accessDto = JwtFullDto.builder()
                .accessToken(generateAccessToken(userId))
                .refreshToken(refreshToken.getValue())
                .build();

        return accessDto;
    }
    @Override
    @Transactional
    public void delete(String token) {
        var refreshToken = repository.findByValue(token).orElseThrow(() ->
                new NotFoundException()
        );

        repository.delete(refreshToken);
    }
    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(getRefreshKey()).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public boolean expiredToken(String token) {
        final var expiredTicks = extractExpiredTicks(token);
        final var expiredDate = new Date(expiredTicks * 1000);
        final var now = new Date();

        return now.after(expiredDate);
    }
    @Override
    public Long extractId(String token) {
        return ((Number) extractClaims(token).get(ID_KEY)).longValue();
    }

    private Long extractExpiredTicks(String token) {
        return (Long) extractClaims(token).get(EXPIRED_KEY);
    }

    private String generateAccessToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_KEY, userId);
        var permissions = permissionRepository.findAllByUserId(userId);
        claims.put(SCOPE_KEY, permissions.stream().map(x -> x.getScope().getName()).collect(Collectors.toList()));
        final long ticksExpired = 15 * 60 * 1000;

        return generateToken(claims, getAccessKey(), ticksExpired);
    }

    private String generateRefreshToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_KEY, user.getId());

        final long ticksExpired = 30L * 24 * 60 * 60 * 1000;
        String refreshToken = generateToken(claims, getRefreshKey(), ticksExpired);

        TokenEntity token = new TokenEntity();
        token.setCreatedAt(System.currentTimeMillis());
        token.setUser(user);
        token.setValue(refreshToken);
        repository.save(token);

        return refreshToken;
    }

    private String generateToken(Map<String, Object> claims, Key key, Long ticks) {
        return Jwts.builder().claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(System.currentTimeMillis() + ticks))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getAccessKey() {
        byte[] bytesOfKey = Decoders.BASE64.decode("thisIsMyAccessKeysadddddddddddddddddddddddddd");
        return Keys.hmacShaKeyFor(bytesOfKey);
    }

    private Key getRefreshKey() {
        byte[] bytesOfKey = Decoders.BASE64.decode("thisIsMyRefreshTokenasdasjkdhasjkfhasdbhasada");
        return Keys.hmacShaKeyFor(bytesOfKey);
    }
}
