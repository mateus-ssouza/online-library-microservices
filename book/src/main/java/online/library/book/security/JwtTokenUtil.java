package online.library.book.security;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;

public class JwtTokenUtil {

    public static Authentication getAuthentication(String username, String role) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
    }
}
