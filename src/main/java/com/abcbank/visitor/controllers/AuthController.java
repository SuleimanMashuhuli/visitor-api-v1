package com.abcbank.visitor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.Auth.ChangePasswordRequestDto;
import com.abcbank.visitor.domain.dto.Auth.LoginRequestDto;
import com.abcbank.visitor.domain.dto.Auth.LoginResponseDto;
import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.repositories.UserRepository;
import com.abcbank.visitor.security.JwtService;
import com.abcbank.visitor.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j 
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("======================================= Login: {}", loginRequestDto);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        String token = jwtService.generateToken(authentication);
        User user = userRepository
                                .findByUsername(loginRequestDto.getUsername())
                                .orElseThrow(() -> 
                                new UsernameNotFoundException(
                                    "User not found: " + loginRequestDto.getUsername()
                                ));
        LoginResponseDto response = LoginResponseDto
                                .builder()
                                .token(token)
                                .passwordChangeRequired(user.isPasswordChangeRequired())
                                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequestDto dto, Authentication authentication) {
        userService.changePassword(authentication.getName(), dto);
        return ResponseEntity.noContent().build();
    }
}
