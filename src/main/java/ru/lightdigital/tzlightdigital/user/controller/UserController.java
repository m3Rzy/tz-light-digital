package ru.lightdigital.tzlightdigital.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
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

//    todo: верный эндпоинт
//    @PostMapping
//    public User saveUser(@RequestBody UserDtoInput userDtoInput) {
//        User user = User.builder()
//                        .id(userDtoInput.getId())
//                        .name(userDtoInput.getName())
//                        .phone(userDtoInput.getPhone())
//                        .password(userDtoInput.getPassword())
//                        .role("ROLE_USER")
//                        .build();
//        return userService.add(user);
//    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.add(user);
    }

    @PatchMapping("/{id}")
    public User changeRole(@PathVariable long id) {
        return userService.changeRole(id);
    }
}
