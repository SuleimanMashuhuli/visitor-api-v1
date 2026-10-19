package com.abcbank.visitor.domain.dto.Auth;

import com.abcbank.visitor.domain.dto.UserResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private boolean passwordChangeRequired;
    private UserResponseDto user;
}