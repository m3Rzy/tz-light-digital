package ru.lightdigital.tzlightdigital.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public List<User> readUsers() {
        return userService.getAll();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.add(user);
    }
}
