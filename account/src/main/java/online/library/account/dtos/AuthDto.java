package online.library.account.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @NotNull(message = "Login is required")
    @Size(min = 3, max = 60, message = "Login must be between 3 and 60 characters")
    private String login;

    @NotNull(message = "Password is required")
    @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters")
    private String password;
}
