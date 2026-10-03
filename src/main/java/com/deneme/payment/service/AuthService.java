package com.deneme.payment.service;

import com.deneme.payment.dto.AuthResponse;
import com.deneme.payment.dto.LoginRequest;
import com.deneme.payment.dto.RegisterRequest;
import com.deneme.payment.entity.Role;
import com.deneme.payment.entity.User;
import com.deneme.payment.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = repository.findByEmail(request.email())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}