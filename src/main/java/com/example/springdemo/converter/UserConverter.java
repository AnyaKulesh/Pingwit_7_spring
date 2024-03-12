package com.example.springdemo.converter;

import com.example.springdemo.controller.CreateUserInputDto;
import com.example.springdemo.controller.UserDto;
import com.example.springdemo.repository.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto convertToUserDto(User user) {
        return new UserDto(user.id(), user.name() + " " + user.surname(), user.email());
    }

    public User convertToUser (CreateUserInputDto input){
        return new User(null, input.getName(),input.getSurname(), input.getEmail(), input.getPhone());
    }
}
