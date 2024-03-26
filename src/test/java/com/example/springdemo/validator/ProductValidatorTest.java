package com.example.springdemo.validator;

import com.example.springdemo.controller.product.CreateProductInputDto;
import com.example.springdemo.exception.PingwitValidationException;
import com.example.springdemo.validator.product.ProductValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductValidatorTest {

    private final ProductValidator productValidator = new ProductValidator();

    @Test
    void testSuccess() {
        CreateProductInputDto product = new CreateProductInputDto();
        product.setName("Product");
        product.setDescription("description");
        product.setPrice(BigDecimal.valueOf(1.35));

        Assertions.assertDoesNotThrow(() -> productValidator.validateOnCreate(product));
    }

    @Test
    void testAllInvalid() {
        CreateProductInputDto product = new CreateProductInputDto();
        product.setName("   ");
        product.setDescription("");
        product.setPrice(BigDecimal.ZERO);

        PingwitValidationException exception = Assertions.assertThrows(PingwitValidationException.class,
                () -> productValidator.validateOnCreate(product));
        assertThat(exception.getViolations()).contains(ProductValidator.BLANK_NAME_ERROR);
        assertThat(exception.getViolations()).contains(ProductValidator.BLANK_SURNAME_ERROR);
        assertThat(exception.getViolations()).contains(ProductValidator.POSITIVE_PRICE_ERROR);
    }

    @Test
    void testDescriptionWithDigits() {
        CreateProductInputDto product = new CreateProductInputDto();
        product.setName("Product2");
        product.setDescription("Product in 2 boxes");
        product.setPrice(BigDecimal.valueOf(1.49));

        PingwitValidationException exception = Assertions.assertThrows(PingwitValidationException.class,
                () -> productValidator.validateOnCreate(product));
        assertThat(exception.getViolations()).contains(ProductValidator.ONLY_LETTERS_NAME_ERROR);
        assertThat(exception.getViolations()).contains(ProductValidator.ONLY_LETTERS_DESCRIPTION_ERROR);
    }

}