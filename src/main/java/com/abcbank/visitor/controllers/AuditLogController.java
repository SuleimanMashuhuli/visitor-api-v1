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
@RequestMapping(path = "/api/v1/audit-logs")
@RequiredArgsConstructor 
@RolesAllowed({"ADMIN"})
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping("/{auditLogId}")
    public ResponseEntity<AuditLogResponseDto> get(@PathVariable Integer auditLogId) {
        AuditLogResponseDto res = auditLogService.getAuditLog(auditLogId);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<AuditLogResponseDto>> getAll() {
        List<AuditLogResponseDto> response = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponseDto>> getLogUser(@PathVariable Integer userId) {
        List<AuditLogResponseDto> dto = auditLogService.getAuditLogsByUser(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/entity/{type}/{entityId}")
    public ResponseEntity<List<AuditLogResponseDto>> getLogEntity(@PathVariable String entityType, @PathVariable Integer entityId) {
        List<AuditLogResponseDto> dtoRes = auditLogService.getAuditLogsByEntity(entityType, entityId);
        return ResponseEntity.ok(dtoRes);
    }
}