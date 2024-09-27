package online.library.book.dtos;

import online.library.book.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Category category;
    private Integer totalCopies;
}
