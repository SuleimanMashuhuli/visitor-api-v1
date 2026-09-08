package com.abcbank.visitor.domain.dto;

import jakarta.validation.constraints.NotBlank;
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
public class VisitorRequestDto {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private String email;
    private String phoneNumber;
    @NotBlank private String idType;
    @NotBlank private String idNumber;
    private String organization;
    private int visitId;
}
