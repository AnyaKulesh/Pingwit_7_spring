package com.example.springdemo.service;

import com.example.springdemo.controller.UserDto;
import com.example.springdemo.repository.User;
import com.example.springdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAllUsers().stream()
                .map(this::mapToDto)
                .toList();
    }

    public UserDto findUserById(Integer id) {
        Optional<User> userById = userRepository.findUserById(id);
        return userById.map(this::mapToDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    private UserDto mapToDto(User user) {
        return new UserDto(user.id(), user.name() + " " + user.surname(), user.email());
    }
}
