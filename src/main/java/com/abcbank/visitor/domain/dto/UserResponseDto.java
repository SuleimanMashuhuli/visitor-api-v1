package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String employeeId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String branch;
    private boolean active;
    private boolean passwordChangeRequired;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}