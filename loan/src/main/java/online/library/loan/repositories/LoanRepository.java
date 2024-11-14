package online.library.loan.repositories;

import online.library.loan.models.Loan;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByUserId(Long userId);

    Optional<Loan> findByIdAndUserId(Long id, Long userId);
}
