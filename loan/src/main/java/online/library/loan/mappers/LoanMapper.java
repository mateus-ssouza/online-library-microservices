package online.library.loan.mappers;

import online.library.loan.dtos.CreateLoanRequestDto;
import online.library.loan.dtos.LoanResponseDto;
import online.library.loan.dtos.UpdateLoanRequestDto;
import online.library.loan.models.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoanMapper {

    @Mapping(target = "booksId", source = "booksId")
    @Mapping(target = "fines", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Loan map(CreateLoanRequestDto loanRequest);

    @Mapping(target = "returnDate", source = "returnDate")
    @Mapping(target = "booksId",  ignore = true)
    @Mapping(target = "fines", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "loanDate", ignore = true)
    Loan map(UpdateLoanRequestDto loanRequest);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "loanDate", source = "loanDate", qualifiedByName = "formatDate")
    @Mapping(target = "returnDate", source = "returnDate", qualifiedByName = "formatDate")
    LoanResponseDto map(Loan loan);

    @Named("formatDate")
    default String formatDate(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
}

