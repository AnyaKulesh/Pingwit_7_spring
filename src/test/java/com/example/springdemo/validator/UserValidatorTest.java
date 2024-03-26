package com.example.springdemo.validator;

import com.example.springdemo.controller.user.CreateUserInputDto;
import com.example.springdemo.exception.PingwitValidationException;
import com.example.springdemo.repository.user.UserRepository;
import com.example.springdemo.validator.user.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserValidatorTest {
    private static final Set<String> EMAILS = new HashSet<>(List.of("anya@sobaka.sutulaja", "sanya@moj.dodyr"));

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserValidator userValidator = new UserValidator(userRepository);

    @BeforeEach
    public void setUp() {
        when(userRepository.getAllEmails()).thenReturn(EMAILS);
    }

    @Test
    void testSuccess() {
        CreateUserInputDto user = new CreateUserInputDto();
        user.setName("Dima");
        user.setSurname("Kulesh");
        user.setPhone("80291231212");
        user.setEmail("dimka@fantazer.net");

        Assertions.assertDoesNotThrow(() -> userValidator.validateOnCreate(user));
    }

    @Test
    void testAllInvalid(){
        CreateUserInputDto user = new CreateUserInputDto();
        user.setName("  ");
        user.setSurname(" ");
        user.setPhone("+193004");
        user.setEmail("anyakulesh");

        PingwitValidationException exception = Assertions.assertThrows(PingwitValidationException.class,
                () -> userValidator.validateOnCreate(user));
        assertThat(exception.getViolations()).contains(UserValidator.BLANK_NAME_ERROR);
        assertThat(exception.getViolations()).contains(UserValidator.BLANK_SURNAME_ERROR);
        assertThat(exception.getViolations()).contains(UserValidator.ONLY_DIGITS_ERROR + user.getPhone());
        assertThat(exception.getViolations()).contains(UserValidator.EMAIL_PATTERN_ERROR + user.getEmail());
    }

    @Test
    void testUniqueEmailError(){
        CreateUserInputDto user = new CreateUserInputDto();
        user.setName("Dima");
        user.setSurname("Kulesh");
        user.setPhone("80291231212");
        user.setEmail("anya@sobaka.sutulaja");

        PingwitValidationException exception = Assertions.assertThrows(PingwitValidationException.class,
                () -> userValidator.validateOnCreate(user));
        assertThat(exception.getViolations()).contains("Email " + user.getEmail() + " already exists");
    }
}