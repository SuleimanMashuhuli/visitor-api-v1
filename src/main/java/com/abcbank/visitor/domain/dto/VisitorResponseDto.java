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
public class VisitorResponseDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idType;
    private String idNumber;
    private String organization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
