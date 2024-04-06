package by.betrayal.accountservice.utils.token;

import by.betrayal.accountservice.entity.TokenEntity;
import by.betrayal.accountservice.entity.UserEntity;
import by.betrayal.accountservice.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RefreshTokenUtils implements TokenUtils {

    @Value("${jwt.refresh-secret}")
    private String secret;

    private final TokenRepository repository;

    private final Long TICKS_EXPIRED = 30L * 24 * 60 * 60 * 1000;

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(ID_KEY, user.getId());

        String refreshToken = generateToken(claims, getKey(), TICKS_EXPIRED);

        TokenEntity token = new TokenEntity();
        token.setCreatedAt(System.currentTimeMillis());
        token.setUser(user);
        token.setValue(refreshToken);
        repository.save(token);

        return refreshToken;
    }

    @Override
    public Key getKey() {
        var bytesOfKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytesOfKey);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long extractId(String token) {
        return ((Number) extractClaims(token).get(ID_KEY)).longValue();
    }

    public Long extractExpiredTicks(String token) {
        return (Long) extractClaims(token).get(EXPIRED_KEY);
    }
}
