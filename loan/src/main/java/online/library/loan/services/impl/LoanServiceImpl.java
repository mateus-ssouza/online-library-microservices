package online.library.loan.services.impl;

import online.library.loan.enums.Status;
import online.library.loan.exceptions.ConflictException;
import online.library.loan.exceptions.NotFoundException;
import online.library.loan.exceptions.RepositoryException;
import online.library.loan.exceptions.ValidationException;
import online.library.loan.models.Loan;
import online.library.loan.repositories.LoanRepository;
import online.library.loan.services.LoanService;
import online.library.loan.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            if (loan.getReturnDate() != null && loan.getReturnDate().isBefore(loan.getLoanDate())) {
                Map<String, String> errors = new HashMap<>();
                errors.put(
                        "returnDate",
                        String.format("The returnDate [%s] field cannot be earlier than the loanDate [%s] field.",
                                loan.getReturnDate().toString(), loan.getLoanDate().toString()));
                throw new ValidationException("Validation of the field(s) failed.", errors);
            }

            loan.setStatus(Status.REQUESTED);
            return _loanRepository.save(loan);
        } catch (ConflictException e) {
            throw e;
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage(), e.getErrors());
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

            if (loanUpdated.getStatus() != Status.REQUESTED) {
                throw new ConflictException(Strings.LOAN.ERROR_UNSOLICITED_STATUS);
            }

            if (loan.getReturnDate() != null && loan.getReturnDate().isBefore(loanUpdated.getLoanDate())) {
                Map<String, String> errors = new HashMap<>();
                errors.put(
                        "returnDate",
                        String.format("The returnDate [%s] field cannot be earlier than the loanDate [%s] field.",
                                loan.getReturnDate().toString(), loanUpdated.getLoanDate().toString()));
                throw new ValidationException("Validation of the field(s) failed.", errors);
            }

            loanUpdated.setReturnDate(loan.getReturnDate());

            return _loanRepository.save(loanUpdated);
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage(), e.getErrors());
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

    @Override
    public List<Loan> getAllByUserId(Long userId) {
        try {
            return _loanRepository.findAllByUserId(userId);
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_FIND_ALL_BY_USER_ID, e);
        }
    }

    @Override
    public Optional<Loan> getByIdMyLoan(Long id, Long userId) {
        try {
            Optional<Loan> loan = _loanRepository.findByIdAndUserId(id, userId);

            if (!loan.isPresent()) {
                throw new NotFoundException(Strings.LOAN.NOT_FOUND_OR_NOT_ASSOCIATED);
            }

            return loan;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_FIND_BY_ID_AND_USER_ID, e);
        }
    }

    @Override
    public void validate(Long id) {
        try {
            Optional<Loan> loan = getById(id);

            if (loan.isPresent()) {
                Loan loanValidate = loan.get();

                if (loanValidate.getStatus() != Status.REQUESTED) {
                    throw new ConflictException(Strings.LOAN.ERROR_UNSOLICITED_STATUS);
                }

                if (loanValidate.getLoanDate().isBefore(LocalDate.now())) {
                    long differenceInDays = ChronoUnit.DAYS.between(loanValidate.getLoanDate(),
                            loanValidate.getReturnDate());
                    loanValidate.setLoanDate(LocalDate.now());
                    loanValidate.setReturnDate(LocalDate.now().plusDays(differenceInDays));
                }

                loanValidate.setStatus(Status.IN_PROGRESS);
                _loanRepository.save(loanValidate);

            } else {
                throw new NotFoundException(Strings.LOAN.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_VALIDATE, e);
        }
    }

    @Override
    public void finalize(Long id) {
        try {
            Optional<Loan> loan = getById(id);

            if (loan.isPresent()) {
                Loan loanFinalize = loan.get();

                if (loanFinalize.getStatus() != Status.IN_PROGRESS) {
                    throw new ConflictException(Strings.LOAN.ERROR_IN_PROGRES_STATUS);
                }

                long differenceInDays = ChronoUnit.DAYS.between(loanFinalize.getReturnDate(), LocalDate.now());
                if (differenceInDays > 0) {
                    loanFinalize.setFines(differenceInDays * 0.3);
                }

                loanFinalize.setStatus(Status.FINISHED);
                _loanRepository.save(loanFinalize);

            } else {
                throw new NotFoundException(Strings.LOAN.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.LOAN.ERROR_FINALIZE, e);
        }
    }
}
