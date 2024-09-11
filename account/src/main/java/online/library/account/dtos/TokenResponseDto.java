package online.library.account.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDto(String accessToken, String refreshToken) {
}
