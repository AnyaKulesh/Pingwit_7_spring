package com.example.springdemo.converter.user;

import com.example.springdemo.controller.user.CreateUserInputDto;
import com.example.springdemo.controller.user.UserDto;
import com.example.springdemo.repository.user.User;
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
