package online.library.loan.dtos;

import lombok.Getter;
import lombok.Setter;
import online.library.loan.enums.Status;

import java.util.List;

@Getter
@Setter
public class LoanResponseDto {
    private Long id;
    private String loanDate;
    private String returnDate;
    private Status status;
    private double fines;
    private Long userId;
    private List<Long> booksId;
}
