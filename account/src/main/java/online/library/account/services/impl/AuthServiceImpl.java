package online.library.account.services.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import online.library.account.dtos.AuthDto;
import online.library.account.dtos.RefreshTokenResponseDto;
import online.library.account.dtos.TokenResponseDto;
import online.library.account.exceptions.NotFoundException;
import online.library.account.models.User;
import online.library.account.repositories.UserRepository;
import online.library.account.services.AuthService;
import online.library.account.utils.Strings;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository _userRepository;

    @Value("${auth.jwt.token.secret}")
    private String secret;

    @Value("${auth.jwt.token.expiration}")
    private Integer tokenExpirationTime;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer refreshTokenExpirationTime;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return _userRepository.findByLogin(login);
    }

    @Override
    public TokenResponseDto getToken(AuthDto authDto) {
        User user = (User) _userRepository.findByLogin(authDto.getLogin());

        return TokenResponseDto
                .builder()
                .accessToken(generateToken(user, tokenExpirationTime))
                .refreshToken(generateToken(user, refreshTokenExpirationTime))
                .build();
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    @Override
    public RefreshTokenResponseDto getRefreshToken(String refreshToken) {
        String login = validateToken(refreshToken);
        User user = (User) _userRepository.findByLogin(login);

        if (user == null) {
            throw new NotFoundException((Strings.USER.NOT_FOUND));
        }

        var autentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autentication);

        return RefreshTokenResponseDto
                .builder()
                .accessToken(generateToken(user, tokenExpirationTime))
                .build();
    }

    public String generateToken(User user, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withClaim("userId", user.getId())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(genExpirationDate(expiration))
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException(Strings.ERROR.GENERATE_TOKEN, exception);
        }
    }

    public String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new IllegalArgumentException(Strings.ERROR.INVALID_TOKEN_FORMAT);
    }

    public String getUserLoginFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject(); // Retrieves the "sub" field of the token
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException(Strings.ERROR.INVALID_TOKEN);
        }
    }

    private Instant genExpirationDate(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
