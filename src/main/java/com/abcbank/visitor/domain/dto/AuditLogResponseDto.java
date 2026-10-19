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
public class AuditLogResponseDto {
    private int id;
    private String action;
    private String description;
    private String firstName;
    private String lastName;
    private String operatorLevel;
    private String branch;
    private String ipAddress;
    private LocalDateTime createdAt;
}
