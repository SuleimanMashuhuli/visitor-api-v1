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
public class AuditLogResponseDto {
    private int id;
    private String action;
    private String description;
    private String entityType;
    private int entityId;
    private int userId;
    private int branchId;
    private LocalDateTime createdAt;
}
