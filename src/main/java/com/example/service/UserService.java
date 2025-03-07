package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

import java.util.List;

public class UserService extends MainService<User> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }


}
