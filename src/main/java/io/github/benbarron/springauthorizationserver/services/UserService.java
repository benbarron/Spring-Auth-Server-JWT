package io.github.benbarron.springauthorizationserver.services;

import io.github.benbarron.springauthorizationserver.models.User;
import io.github.benbarron.springauthorizationserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("User not found with username: " + username);
        });
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }
}
