package online.library.loan.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.library.loan.utils.PresentOrFutureDate;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoanRequestDto {
    @NotNull(message = "LoanDate is required")
    @PresentOrFutureDate(field = "LoanDate")
    private String loanDate;

    @NotNull(message = "ReturnDate is required")
    @PresentOrFutureDate(field = "returnDate")
    private String returnDate;

    @NotNull(message = "BooksId is required")
    private List<Long> booksId;
}
