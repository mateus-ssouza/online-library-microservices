package online.library.loan.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.library.loan.utils.PresentOrFutureDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLoanRequestDto {
    @NotNull(message = "ReturnDate is required")
    @PresentOrFutureDate(field = "returnDate")
    private String returnDate;
}
