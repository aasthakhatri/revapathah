package com.revapathah.journey_preparation.service;

import org.springframework.stereotype.Service;

import com.revapathah.journey_preparation.entity.User;
import com.revapathah.journey_preparation.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
