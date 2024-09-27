package online.library.book.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.library.book.enums.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfoCopyDto {
    private String title;
    private String author;
    private Category category;
}
