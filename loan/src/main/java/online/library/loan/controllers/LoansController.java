package online.library.loan.controllers;

import jakarta.validation.Valid;
import online.library.loan.dtos.CreateLoanRequestDto;
import online.library.loan.dtos.LoanResponseDto;
import online.library.loan.dtos.UpdateLoanRequestDto;
import online.library.loan.mappers.LoanMapper;
import online.library.loan.services.LoanService;
import online.library.loan.utils.ValidationErrors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loans")
public class LoansController {

    @Autowired
    private LoanService _loanService;

    @Autowired
    private LoanMapper _loanMapper;

    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        try {
            var loans = _loanService.getAll();
            var loansDto = loans.stream()
                    .map(loan -> _loanMapper.map(loan))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(loansDto);
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id) {
        try {
            return _loanService.getById(id)
                    .map(loan -> ResponseEntity.ok(_loanMapper.map(loan)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody CreateLoanRequestDto loanRequestDto,
                                                      BindingResult validateFields) {
        try {
            ValidationErrors.validateBindingResult(validateFields);
            var loan = _loanMapper.map(loanRequestDto);
            var savedLoan = _loanService.create(loan);
            var loanResponseDto = _loanMapper.map(savedLoan);

            return new ResponseEntity<>(loanResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDto> updateLoan(@PathVariable Long id,
            @Valid @RequestBody UpdateLoanRequestDto loanRequestDto, BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var loan = _loanMapper.map(loanRequestDto);
            var updatedLoan = _loanService.update(id, loan);
            var loanResponseDto = _loanMapper.map(updatedLoan);

            return ResponseEntity.ok(loanResponseDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        try {
            _loanService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }
}
