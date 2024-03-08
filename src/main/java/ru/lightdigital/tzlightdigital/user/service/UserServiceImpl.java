package ru.lightdigital.tzlightdigital.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.repository.UserRepository;
import ru.lightdigital.tzlightdigital.util.exception.AccessException;
import ru.lightdigital.tzlightdigital.util.exception.BadRequestException;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        log.info("Количество пользователей всех ролей: {}", userRepository.findAll().size());
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя не существует с id " + id + "."));
    }

    @Override
    public User changeRole(long id) {
        User user = getById(id);

        if (user.getRole().equals("ROLE_OPERATOR")) {
            log.info("{} уже является оператором!", user);
            return user;
        }
        if (user.getRole().equals("ROLE_USER")) {
            user.setRole("ROLE_OPERATOR");
            userRepository.saveAndFlush(user);
            log.info("{} успешно стал оператором.", user);
            return user;
        }
        if (user.getRole().equals("ROLE_ADMIN")) {
            log.error("{} назначить роль оператора можно только на пользователей!", user);
            throw new BadRequestException("Администратора нельзя понизить!");
        }
        throw new AccessException("Возможно, у Вас нет прав на использование этой команды!");
    }

    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }

        log.info("{} успешно создан.", user);
        return userRepository.save(user);
    }
}
