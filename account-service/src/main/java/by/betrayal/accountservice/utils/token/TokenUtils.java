package by.betrayal.accountservice.utils.token;

import by.betrayal.accountservice.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public interface TokenUtils {

    String ID_KEY = "id";
    String SCOPE_KEY = "scopes";
    String EXPIRED_KEY = "exp";

    Key getKey();

    default String generateToken(Map<String, Object> claims, Key key, Long ticks) {
        return Jwts.builder().claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(System.currentTimeMillis() + ticks))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
