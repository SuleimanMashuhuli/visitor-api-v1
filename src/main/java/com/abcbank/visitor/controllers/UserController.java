package com.abcbank.visitor.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.UserRequestDto;
import com.abcbank.visitor.domain.dto.UserResponseDto;
import com.abcbank.visitor.services.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor 
@RolesAllowed({"ADMIN"})
@Slf4j 
public class UserController {
    private final UserService userService;

    @PostMapping 
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("=============================== Creating-UserRequestDto: {}", userRequestDto);
        UserResponseDto reDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reDto);
    }

    @GetMapping 
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> dto = userService.getAllUsers();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Integer userId) {
        UserResponseDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer userId, @Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("=============================== Updating-UserRequestDto: {}", userRequestDto);
        UserResponseDto res = userService.updateUser(userId, userRequestDto);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}