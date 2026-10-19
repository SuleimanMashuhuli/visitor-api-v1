package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequestDto {
    @NotBlank 
    private String hostEmail;
    private String hostName;
    private String branchName;
    private String purpose;
    private Integer durationMinutes;
    private LocalDateTime scheduledAt; 
    private Integer visitorId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idType;
    private String idNumber;
    private String organization;
}
