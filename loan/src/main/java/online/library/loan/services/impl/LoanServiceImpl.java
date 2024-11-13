package online.library.loan.services.impl;

import online.library.loan.enums.Status;
import online.library.loan.exceptions.ConflictException;
import online.library.loan.exceptions.NotFoundException;
import online.library.loan.exceptions.RepositoryException;
import online.library.loan.models.Loan;
import online.library.loan.repositories.LoanRepository;
import online.library.loan.services.LoanService;
import online.library.loan.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository _loanRepository;

    @Override
    public List<Loan> getAll() {
        try {
            return _loanRepository.findAll();
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_FIND_ALL_LIST, e);
        }
    }

    @Override
    public Optional<Loan> getById(Long id) {
        try {
            Optional<Loan> loan = _loanRepository.findById(id);

            if (!loan.isPresent()) {
                throw new NotFoundException(Strings.LOAN.NOT_FOUND);
            }

            return loan;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_FIND_BY_ID, e);
        }
    }

    @Override
    public Loan create(Loan loan) {
        try {
            loan.setStatus(Status.REQUESTED);
            loan.setUserId(1L);
            return _loanRepository.save(loan);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_CREATE, e);
        }
    }

    @Override
    public Loan update(Long id, Loan loan) {
        try {
            Optional<Loan> loanModel = _loanRepository.findById(id);

            // Check if a loan exists by id
            if (!loanModel.isPresent())
                throw new NotFoundException(Strings.LOAN.NOT_FOUND);

            // Updating loan fields
            Loan loanUpdated = loanModel.get();
            loanUpdated.setReturnDate(loan.getReturnDate());

            return _loanRepository.save(loanUpdated);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_UPDATE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (_loanRepository.existsById(id)) {
                // Removing loan by their id
                _loanRepository.deleteById(id);
            } else {
                throw new NotFoundException(Strings.LOAN.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_DELETE, e);
        }
    }
}
