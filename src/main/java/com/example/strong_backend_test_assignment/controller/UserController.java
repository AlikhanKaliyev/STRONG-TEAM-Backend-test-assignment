package com.example.strong_backend_test_assignment.controller;

import com.example.strong_backend_test_assignment.domain.dto.userDTO.UserLoginDTO;
import com.example.strong_backend_test_assignment.domain.dto.userDTO.UserRegisterDTO;
import com.example.strong_backend_test_assignment.domain.dto.userDTO.UserTokenResponseDTO;
import com.example.strong_backend_test_assignment.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<UserTokenResponseDTO> register(
            @RequestBody UserRegisterDTO request
    ) {
        UserTokenResponseDTO result = service.register(request);
        if(result.getToken() == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserTokenResponseDTO> authenticate(
            @RequestBody UserLoginDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
