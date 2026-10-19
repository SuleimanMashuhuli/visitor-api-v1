package com.abcbank.visitor.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String employeeId;
    private String username;
    private String firstName;
    private String lastName;
    @Email 
    private String email;
    private String phoneNumber;
    private String role;
    private String branch;
}
