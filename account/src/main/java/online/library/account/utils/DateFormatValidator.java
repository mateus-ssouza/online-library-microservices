package online.library.account.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null || date.isEmpty()) {
            return true;
        }
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
