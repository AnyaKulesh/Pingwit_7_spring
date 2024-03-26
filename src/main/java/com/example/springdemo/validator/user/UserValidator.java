package com.example.springdemo.validator.user;

import com.example.springdemo.controller.user.CreateUserInputDto;
import com.example.springdemo.exception.PingwitValidationException;
import com.example.springdemo.repository.user.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    public static final String BLANK_NAME_ERROR = "Name is blank";
    public static final String BLANK_SURNAME_ERROR = "Surname is blank";
    public static final String EMAIL_PATTERN_ERROR = "Email is invalid: ";
    public static final String ONLY_DIGITS_ERROR = "Phone can contain only numbers: ";


    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d+");

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateOnCreate(CreateUserInputDto input) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(input.getName().trim())) {
            errors.add(BLANK_NAME_ERROR);
        }
        if (StringUtils.isBlank(input.getSurname().trim())) {
            errors.add(BLANK_SURNAME_ERROR);
        }
        if (!EMAIL_PATTERN.matcher(input.getEmail()).matches()) {
            errors.add(EMAIL_PATTERN_ERROR + input.getEmail());
        }
        if (!PHONE_NUMBER_PATTERN.matcher(input.getPhone()).matches()) {
            errors.add(ONLY_DIGITS_ERROR + input.getPhone());
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
