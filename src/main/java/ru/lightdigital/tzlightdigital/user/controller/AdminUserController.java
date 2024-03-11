package ru.lightdigital.tzlightdigital.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@AllArgsConstructor
public class AdminUserController {

    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> readUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User readUserById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User changeRole(@PathVariable long id) {
        return userService.changeRole(id);
    }
}
