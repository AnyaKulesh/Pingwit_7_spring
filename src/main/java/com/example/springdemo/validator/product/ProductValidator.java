package com.example.springdemo.validator.product;

import com.example.springdemo.controller.product.CreateProductInputDto;
import com.example.springdemo.exception.PingwitValidationException;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 Правила валидации: имя продукта - не пустая строка, не содержит символов $.
Описание (оно опциональное) - не пустая строка, не содержит символов $. Цена - не отрицательная и не 0.

 */
@Component
public class ProductValidator {

    public static final String BLANK_NAME_ERROR = "Name is blank";
    public static final String BLANK_SURNAME_ERROR = "Description is blank";
    public static final String ONLY_LETTERS_NAME_ERROR = "Name should have only letters";
    public static final String ONLY_LETTERS_DESCRIPTION_ERROR = "Description should have only letters";
    public static final String POSITIVE_PRICE_ERROR = "Price should be more than zero";

    private static final Pattern ONLY_LETTERS_PATTERN = Pattern.compile("^[a-zA-Z]*$");

    public void validateOnCreate(CreateProductInputDto inputDto){
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(inputDto.getName().trim())) {
            errors.add(BLANK_NAME_ERROR);
        }
        if(!ONLY_LETTERS_PATTERN.matcher(inputDto.getName()).matches()){
            errors.add(ONLY_LETTERS_NAME_ERROR);
        }
        if(inputDto.getDescription()!= null){
        if (StringUtils.isBlank(inputDto.getDescription().trim())) {
            errors.add(BLANK_SURNAME_ERROR);
        }
        if(!ONLY_LETTERS_PATTERN.matcher(inputDto.getDescription()).matches()){
            errors.add(ONLY_LETTERS_DESCRIPTION_ERROR);
        }
        }
        if (inputDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(POSITIVE_PRICE_ERROR);
        }

        if (!errors.isEmpty()) {
            throw new PingwitValidationException("Product data is invalid: ", errors);
        }

    }

}
