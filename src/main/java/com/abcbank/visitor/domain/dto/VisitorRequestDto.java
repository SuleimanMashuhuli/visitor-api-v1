package com.abcbank.visitor.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorRequestDto {
    @NotBlank 
    private String firstName;
    @NotBlank 
    private String lastName;
    @Email 
    private String email;
    private String phoneNumber;
    @NotBlank 
    private String idType;
    @NotBlank 
    private String idNumber;
    private String organization;
}
