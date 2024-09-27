package online.library.book.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.library.book.enums.Category;
import online.library.book.utils.ValueOfEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequestDto {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 60, message = "Title must be between 3 and 60 characters")
    String title;

    @NotBlank(message = "Author is required")
    @Size(min = 3, max = 60, message = "Author must be between 3 and 60 characters")
    String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 3, max = 45, message = "ISBN must be between 3 and 45 characters")
    String isbn;

    @ValueOfEnum(fieldName = "category", enumClass = Category.class)
    String category;
}
