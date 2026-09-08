package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

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
public class VisitRequestDto {
    private String hostEmployeeId;
    @NotBlank private String hostEmail;
    private String hostName;
    @NotBlank private String purpose;
    private LocalDateTime scheduledAt; 
    private int visitorId;
}
