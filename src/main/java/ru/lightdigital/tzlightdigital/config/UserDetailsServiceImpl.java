package ru.lightdigital.tzlightdigital.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.user.repository.UserRepository;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhone(username)
                .orElseThrow(() -> new NotFoundException("Ошибка с телефоном при аутентифкации!"));
    }
}
