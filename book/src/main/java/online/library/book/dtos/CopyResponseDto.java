package online.library.book.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyResponseDto {
    private Long id;
    private String copyCode;
    private boolean available;
    private BookInfoCopyDto book;
}