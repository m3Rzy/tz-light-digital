package ru.lightdigital.tzlightdigital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByPhone(phone);
        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(phone + " not found!"));
    }
}
