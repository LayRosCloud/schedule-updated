package by.betrayal.accountservice.utils.token;

import by.betrayal.accountservice.repository.PermissionRepository;
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
public class AccessTokenUtils implements TokenUtils {

    @Value("${jwt.access-secret}")
    private String secret;

    private final PermissionRepository permissionRepository;

    private final long TICKS_EXPIRED = 15 * 60 * 1000;

    public String generateToken(Long userId) {
        var permissions = permissionRepository.findAllByUserId(userId);

        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_KEY, userId);
        claims.put(SCOPE_KEY, permissions.stream()
                .map(x -> x.getScope().getName())
                .collect(Collectors.toList()
                )
        );

        return generateToken(claims, getKey(), TICKS_EXPIRED);
    }

    @Override
    public Key getKey() {
        var bytesOfKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytesOfKey);
    }
}
