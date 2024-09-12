package online.library.book.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import online.library.book.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrors {

    public static void validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            throw new ValidationException("Validation of the field(s) failed.", errors);
        }
    }
}