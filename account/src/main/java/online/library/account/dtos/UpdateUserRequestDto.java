package online.library.account.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.library.account.utils.PastDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    @NotNull(message = "Name is required")
    @Size(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    String name;

    @NotNull(message = "CPF is required")
    @Pattern(regexp = "\\d{11}", message = "CPF must have 11 digits")
    String cpf;

    @NotNull(message = "Birthdate is required")
    @PastDate(message = "Birthdate must be a past date")
    LocalDate birthDate;

    @NotNull(message = "Phone number is required")
    @Size(min = 10, max = 13, message = "Phone number must be between 10 and 13 characters")
    String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 60, message = "Email must be no more than 60 characters")
    String email;

    @NotNull(message = "Login is required")
    @Size(min = 3, max = 60, message = "Login must be between 3 and 60 characters")
    String login;
}
