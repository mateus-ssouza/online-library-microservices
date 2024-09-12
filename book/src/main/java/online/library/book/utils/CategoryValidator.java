package online.library.book.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.library.book.enums.Category;

public class CategoryValidator implements ConstraintValidator<ValidCategory, Category> {

    private List<String> acceptedCategories;

    @Override
    public void initialize(ValidCategory constraintAnnotation) {
        // Listar todas as categorias disponíveis no enum Category
        acceptedCategories = Arrays.stream(Category.values())
                .map(Category::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Category value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // ou true, dependendo se a categoria pode ser nula
        }

        // Verificar se o valor fornecido está na lista de categorias permitidas
        if (!acceptedCategories.contains(value.name())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid category. Available categories are: " + String.join(", ", acceptedCategories))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}