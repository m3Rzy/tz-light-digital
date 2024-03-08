package ru.lightdigital.tzlightdigital.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.repository.UserRepository;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

//    Администратор может смотреть список всех пользователей
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));
    }

//    Администратор может назначить пользователя ролью оператора.
    @Override
    public User changeRole(Long id) {
        User user = getById(id);
        if (user.getRole().equals("ROLE_USER, ROLE_OPERATOR")) {
            log.info("{} уже является оператором!", user);
            return user;
        }
        user.setRole(user.getRole() + ", ROLE_OPERATOR");
        log.info("{} успешно стал оператором.", user);
        return user;
    }

//    Метод простой регистрации
    @Override
    public User add(User user) {
        return userRepository.save(user);
    }
}
