package com.example.brahmi.service;


import com.example.brahmi.dto.UserDto;
import com.example.brahmi.entity.User;


import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}