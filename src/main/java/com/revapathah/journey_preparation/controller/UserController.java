package com.revapathah.journey_preparation.controller;
import org.springframework.web.bind.annotation.*;

import com.revapathah.journey_preparation.entity.User;
import com.revapathah.journey_preparation.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
