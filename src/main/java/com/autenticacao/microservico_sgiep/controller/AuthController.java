package com.autenticacao.microservico_sgiep.controller;

import com.autenticacao.microservico_sgiep.dto.LoginRequestDTO;
import com.autenticacao.microservico_sgiep.dto.RegisterRequestDTO;
import com.autenticacao.microservico_sgiep.dto.ResponseDTO;
import com.autenticacao.microservico_sgiep.infra.security.TokenService;
import com.autenticacao.microservico_sgiep.model.User.User;
import com.autenticacao.microservico_sgiep.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.autenticacao.microservico_sgiep.enums.UserRole;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getId(), user.getName(), token, user.getRole().getRole()));
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setAddress(body.address());
            newUser.setPhone(body.phone());

            if (Objects.equals(body.role(), "manager") || Objects.equals(body.role(), "professor")) {
                newUser.setActive(false);
            } else {
                newUser.setActive(true);
            }

            UserRole role = UserRole.valueOf(body.role().toUpperCase());
            newUser.setRole(role);

            this.repository.save(newUser);

            return ResponseEntity.ok("User registered successfully");
        }

        return ResponseEntity.badRequest().body("User already exists");
    }
}