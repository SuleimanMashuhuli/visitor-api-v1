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
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor 
@RolesAllowed({"SYSTEM_ADMIN", "SECURITY_ADMIN"})
public class UserController {
    private final UserService userService;

    @PostMapping 
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto reDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reDto);
    }

    @GetMapping 
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> dto = userService.getAllUsers();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Integer userId) {
        UserResponseDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer userId, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto res = userService.updateUser(userId, userRequestDto);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserResponseDto> assign(@PathVariable Integer userId, @PathVariable Integer roleId) {
        UserResponseDto response = userService.assignRole(userId, roleId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserResponseDto> remove(@PathVariable Integer userId, @PathVariable Integer roleId) {
        UserResponseDto res = userService.removeRole(userId, roleId);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/branch/{branchId}")
    public ResponseEntity<UserResponseDto> add(@PathVariable Integer userId, @PathVariable Integer branchId) {
        UserResponseDto resDto = userService.assignToBranch(userId, branchId);
        return ResponseEntity.ok(resDto);
    }

    // @DeleteMapping("/{id}/branch/{branchId}")
    // public ResponseEntity<UserResponseDto> reset(@PathVariable Integer userId, @PathVariable Integer branchId) {
    //     UserResponseDto response = userService.removeFromBranch(userId, branchId);
    //     return ResponseEntity.ok(response);
    // }
}