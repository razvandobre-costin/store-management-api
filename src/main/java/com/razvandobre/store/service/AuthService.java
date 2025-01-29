package com.razvandobre.store.service;

import com.razvandobre.store.model.User;
import com.razvandobre.store.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isPresent() && passwordEncoder.matches(password, foundUser.get().getPassword())) {
            return foundUser;
        }

        return Optional.empty();
    }
}
