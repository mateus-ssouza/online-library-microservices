package online.library.book.utils;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

    private List<String> acceptedValues;
    private String fieldName;

    @Override
    public void initialize(ValueOfEnum annotation) {
        // Cria a lista de valores aceitos a partir da enum
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
        fieldName = annotation.fieldName();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || !acceptedValues.contains(value.toString())) {
            // Se o valor não for válido, cria uma mensagem customizada com a lista de enums
            context.disableDefaultConstraintViolation();
            String errorMessage = MessageFormat.format(
                    "Invalid {0}. Valid values are: {1}",
                    fieldName,
                    String.join(", ", acceptedValues));
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
            return false;
        }

        return true;
    }
}