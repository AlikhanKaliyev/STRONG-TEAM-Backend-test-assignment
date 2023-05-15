package com.example.strong_backend_test_assignment.service;

import com.example.strong_backend_test_assignment.config.JwtService;
import com.example.strong_backend_test_assignment.domain.dto.userDTO.UserLoginDTO;
import com.example.strong_backend_test_assignment.domain.dto.userDTO.UserRegisterDTO;
import com.example.strong_backend_test_assignment.domain.dto.userDTO.UserTokenResponseDTO;
import com.example.strong_backend_test_assignment.domain.model.Role;
import com.example.strong_backend_test_assignment.domain.model.User;
import com.example.strong_backend_test_assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserTokenResponseDTO register(UserRegisterDTO request) {
        if(repository.existsByEmail(request.getEmail())) {
            return UserTokenResponseDTO
                    .builder()
                    .build();
        }
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return UserTokenResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }

    public UserTokenResponseDTO authenticate(UserLoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return UserTokenResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }
}
