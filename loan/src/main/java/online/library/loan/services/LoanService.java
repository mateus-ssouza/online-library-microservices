package online.library.loan.services;

import online.library.loan.models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    public List<Loan> getAll();

    public Optional<Loan> getById(Long id);

    public List<Loan> getAllByUserId(Long userId);

    public Optional<Loan> getByIdMyLoan(Long id, Long userId);


    public Loan create(Loan loan);

    public Loan update(Long id, Loan loan);

    public void delete(Long id);

    public void validate(Long id);

    public void finalize(Long id);
}
