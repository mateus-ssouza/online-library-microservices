package online.library.account.dtos;

import lombok.Builder;

@Builder
public record RefreshTokenResponseDto(String accessToken) {
}
