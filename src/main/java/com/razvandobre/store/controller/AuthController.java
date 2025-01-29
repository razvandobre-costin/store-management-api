package com.razvandobre.store.controller;

import com.razvandobre.store.model.User;
import com.razvandobre.store.service.AuthService;
import com.razvandobre.store.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Optional<User> foundUser = authService.login(user.getUsername(), user.getPassword());

        if (foundUser.isPresent()) {
            String token = jwtUtil.generateToken(foundUser.get().getUsername(), foundUser.get().getRoles());
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}
