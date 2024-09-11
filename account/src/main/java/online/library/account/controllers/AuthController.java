package online.library.account.controllers;

import jakarta.validation.Valid;
import online.library.account.dtos.AuthDto;
import online.library.account.dtos.RefreshTokenRequestDto;
import online.library.account.dtos.RefreshTokenResponseDto;
import online.library.account.dtos.TokenResponseDto;
import online.library.account.dtos.CreateUserRequestDto;
import online.library.account.dtos.UserResponseDto;
import online.library.account.exceptions.AuthException;
import online.library.account.models.User;
import online.library.account.services.AuthService;
import online.library.account.services.UserService;
import online.library.account.utils.MapperConverter;
import online.library.account.utils.Strings;
import online.library.account.utils.ValidationErrors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager _authManager;
    
    @Autowired
    private UserService _userService;
    
    @Autowired
    private AuthService _authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid AuthDto authDto,
            BindingResult validateFields) {
        try {
            ValidationErrors.validateBindingResult(validateFields);

            var userAutenticationToken = new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword());
            
            this._authManager.authenticate(userAutenticationToken);

            var token = _authService.getToken(authDto);

            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            throw new AuthException(Strings.AUTH.ERROR_CREDENTIALS);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid CreateUserRequestDto userRequestDTO,
            BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var user = MapperConverter.convertToEntity(userRequestDTO, User.class);
            var savedUser = _userService.create(user);

            var userResponseDTO = MapperConverter.convertToDto(savedUser, UserResponseDto.class);

            return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refrestTokenDto,
            BindingResult validateFields) {
        try {
            ValidationErrors.validateBindingResult(validateFields);

            var refreshToken = _authService.getRefreshToken(refrestTokenDto.refreshToken());

            return ResponseEntity.ok(refreshToken);

        } catch (AuthenticationException e) {
            throw new AuthException(Strings.AUTH.ERROR_CREDENTIALS);
        } catch (Exception e) {
            throw e;
        }
    }
}