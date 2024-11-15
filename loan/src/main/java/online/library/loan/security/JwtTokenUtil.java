package online.library.loan.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;

import java.security.Key;

@Component
public class JwtTokenUtil {

    @Value("${auth.jwt.token.secret}")
    private String secret;

    public static Authentication getAuthentication(String username, String role) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
    }

    public <T> T getClaimFromToken(String token, String claimKey, Class<T> claimClass) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.get(claimKey, claimClass);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting claim from token: " + e.getMessage(), e);
        }
    }

    private Claims getAllClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(cleanToken(token))
                .getBody();
    }

    private String cleanToken(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
