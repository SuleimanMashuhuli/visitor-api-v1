package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

import com.abcbank.visitor.domain.enums.BranchName;

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
    private String entityType;
    private int entityId;
    private int userId;
    private BranchName branchName;
    private LocalDateTime createdAt;
}
