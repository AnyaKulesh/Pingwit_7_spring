package com.example.springdemo.validator;

import com.example.springdemo.controller.CreateProductInputDto;
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
    private static final Pattern ONLY_LETTERS_PATTERN = Pattern.compile("^[a-zA-Z]*$");

    public void validateOnCreate(CreateProductInputDto inputDto){
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(inputDto.getName())) {
            errors.add("Name is blank");
        }
        if(!ONLY_LETTERS_PATTERN.matcher(inputDto.getName()).matches()){
            errors.add("Name should have only letters");
        }
        if(inputDto.getDescription()!= null){
        if (StringUtils.isBlank(inputDto.getDescription())) {
            errors.add("Description is blank");
        }
        if(!ONLY_LETTERS_PATTERN.matcher(inputDto.getDescription()).matches()){
            errors.add("Description should have only letters");
        }
        }
        if (inputDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Price should be more than zero");
        }

        if (!errors.isEmpty()) {
            throw new PingwitValidationException("Product data is invalid: ", errors);
        }

    }

}
