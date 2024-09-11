package online.library.account.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import online.library.account.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String cpf;
    private String birthDate;
    private String phone;
    private String email;
    private String createdAt;
    private String login;
    private UserRole role;

    public void setDataNascimento(LocalDate birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.birthDate = birthDate.format(formatter);
    }

    public void setDataCadastro(LocalDate createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.createdAt = createdAt.format(formatter);
    }
}