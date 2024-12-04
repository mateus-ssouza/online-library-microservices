package online.library.loan.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PresentOrFutureDateValidator implements ConstraintValidator<PresentOrFutureDate, String> {

    private String field;

    @Override
    public void initialize(PresentOrFutureDate constraintAnnotation) {
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ISO_DATE);

            boolean isValid = !date.isBefore(LocalDate.now());

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                    "The " + field + " field must contain a current or future date."
                ).addConstraintViolation();
            }

            return isValid;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "The " + field + " field must contain a valid date in the format yyyy-MM-dd."
            ).addConstraintViolation();
            return false;
        }
    }
}

