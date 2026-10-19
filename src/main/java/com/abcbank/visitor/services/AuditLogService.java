package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;

public interface AuditLogService {

    void recordEvent(String action, String description, String entityType);
    AuditLogResponseDto getAuditLog(Integer auditLogId);
    List<AuditLogResponseDto> getAllAuditLogs();
    List<AuditLogResponseDto> getAuditLogsByUser(Integer userId);
    List<AuditLogResponseDto> getAuditLogsByEntity(String entityType);
    List<AuditLogResponseDto> getAuditLogsByBranch(Integer branchId);
}
