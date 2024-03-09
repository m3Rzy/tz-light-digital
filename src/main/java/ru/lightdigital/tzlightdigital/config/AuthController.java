package ru.lightdigital.tzlightdigital.config;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lightdigital.tzlightdigital.user.dto.UserDtoInput;
import ru.lightdigital.tzlightdigital.user.dto.UserDtoOutput;
import ru.lightdigital.tzlightdigital.user.model.User;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        return authService.signUp(user);
    }

    @PostMapping("/signin")
    public UserDtoOutput signIp(@RequestBody UserDtoInput userDtoInput) {
        return authService.signIn(userDtoInput);
    }
}
