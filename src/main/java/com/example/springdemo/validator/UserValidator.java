package com.example.springdemo.validator;

import com.example.springdemo.controller.CreateUserInputDto;
import com.example.springdemo.exception.PingwitValidationException;
import com.example.springdemo.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d+");

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateOnCreate(CreateUserInputDto input) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(input.getName())) {
            errors.add("Name is blank");
        }
        if (StringUtils.isBlank(input.getSurname())) {
            errors.add("Surname is blank");
        }
        if (!EMAIL_PATTERN.matcher(input.getEmail()).matches()) {
            errors.add("Email is invalid: " + input.getEmail());
        }
        if (!PHONE_NUMBER_PATTERN.matcher(input.getPhone()).matches()) {
            errors.add("Phone can contain only numbers: " + input.getPhone());
        }
        if (!validateUniqueEmail(input.getEmail())) {
            errors.add("Email " + input.getEmail() + " already exists");
        }

        if (!errors.isEmpty()) {
            throw new PingwitValidationException("User data is invalid: ", errors);
        }
    }

    private boolean validateUniqueEmail(String email) {
        return !userRepository.getAllEmails().contains(email);
    }
}
