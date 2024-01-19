package pt.ipleiria.estg.dei.ei.dae.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class TokenDecoder {
    protected static final byte[] SECRET_KEY =
            "veRysup3rstr0nginv1ncible5ecretkeY@academics.dae.ipleiria".getBytes();
    protected static final String ALGORITHM = "HMACSHA384";

    public String getUsernameFromToken(String token) {
        String[] parts = token.split(" ");
        String result = parts[parts.length - 1];
        SecretKey key = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(result)
                .getPayload();

        return claims.getSubject();
    }
}
