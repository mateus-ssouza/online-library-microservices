package online.library.account.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import online.library.account.dtos.AuthDto;
import online.library.account.dtos.RefreshTokenResponseDto;
import online.library.account.dtos.TokenResponseDto;

public interface AuthService extends UserDetailsService {
    public TokenResponseDto getToken(AuthDto authDto);
    
    public String validateToken(String token);

    public RefreshTokenResponseDto getRefreshToken(String refreshToken);

}
