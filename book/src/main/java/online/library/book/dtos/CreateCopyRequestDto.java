package online.library.book.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCopyRequestDto {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 60, message = "Title must be between 3 and 60 characters")
    String title;
}
