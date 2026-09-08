package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    private VisitResponseDto visit;
}
