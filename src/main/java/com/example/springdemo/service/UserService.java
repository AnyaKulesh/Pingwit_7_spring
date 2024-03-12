package com.example.springdemo.service;

import com.example.springdemo.controller.CreateUserInputDto;
import com.example.springdemo.controller.UserDto;
import com.example.springdemo.converter.UserConverter;
import com.example.springdemo.repository.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, UserConverter userConverter, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.userValidator = userValidator;
    }

    public List<UserDto> findAll() {
        return userRepository.findAllUsers().stream()
                .map(userConverter :: convertToUserDto)
                .toList();
    }

    public UserDto findUserById(Integer id) {
        Optional<User> userById = userRepository.findUserById(id);
        return userById.map(userConverter :: convertToUserDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    public Integer createUser(CreateUserInputDto input) {

        userValidator.validateOnCreate(input);
        User user = userConverter.convertToUser(input);

        return userRepository.createUser(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteUserById(id);
    }

}
