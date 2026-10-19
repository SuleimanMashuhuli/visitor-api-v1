package com.abcbank.visitor.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;
import com.abcbank.visitor.services.AuditLogService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogResponseDto>> getAll() {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs()
        );
    }

    @GetMapping("/{auditLogId}")
    public ResponseEntity<AuditLogResponseDto> get(@PathVariable Integer auditLogId) {
        return ResponseEntity.ok(auditLogService.getAuditLog(auditLogId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponseDto>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByUser(userId)
        );
    }

    @GetMapping("/entity/{entityType}}")
    public ResponseEntity<List<AuditLogResponseDto>> getByEntity(@PathVariable String entityType) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByEntity(entityType)
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<AuditLogResponseDto>> getByBranch(@PathVariable Integer branchId) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByBranch(branchId)
        );
    }
}