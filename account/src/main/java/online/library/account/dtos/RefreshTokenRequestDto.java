package online.library.account.dtos;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequestDto(@NotNull(message = "RefreshToken is require") String refreshToken) {
}
