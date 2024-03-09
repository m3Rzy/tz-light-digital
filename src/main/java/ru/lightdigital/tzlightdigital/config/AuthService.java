package ru.lightdigital.tzlightdigital.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.user.dto.UserDtoInput;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.repository.UserRepository;
import ru.lightdigital.tzlightdigital.util.exception.BadRequestException;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public User signUp(User user) {
        try {
            if (user.getRole() == null) {
                user.setRole("ROLE_USER");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            log.info("Пользователь {} успешно зарегистрировался.", user);

        } catch (Exception e) {
            throw new BadRequestException("Ошибка регистрации пользователя! Возможно, пользователь уже существует.");
        }
        return user;
    }

    public Token signIn(UserDtoInput userDtoInput) {
        Token token = new Token();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userDtoInput.getPhone(), userDtoInput.getPassword()));
            User user = userRepository.findByPhone(userDtoInput.getPhone())
                    .orElseThrow(() -> new NotFoundException("Такой пользовать не зарегистрирован!"));

            token.setToken(jwtService.generateToken(user));

        } catch (Exception e) {
            throw new BadRequestException("Ошибка авторизации пользователя: " + e.getMessage());
        }
        log.info("{}", token.getToken());
        return token;
    }
}
