package com.reminder.controller;

import com.reminder.config.JwtUtil;
import com.reminder.model.User;
import com.reminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email    = body.get("email");
        String password = body.get("password");

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
        }
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already taken"));
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "username", username,
            "email", email
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "username", user.getUsername(),
            "email", email
        ));
    }
}
