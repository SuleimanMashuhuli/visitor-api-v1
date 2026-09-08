package com.abcbank.visitor.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;
import com.abcbank.visitor.domain.entities.AuditLog;
import com.abcbank.visitor.mappers.SystemMapper;
import com.abcbank.visitor.repositories.AuditLogRepository;
import com.abcbank.visitor.services.AuditLogService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final SystemMapper systemMapper;

    @Override
    public void recordEvent(String action, String description, String entityType, Integer entityId) {
        AuditLog log = AuditLog.builder()
                .action(action)
                .description(description)
                .entityType(entityType)
                .entityId(entityId != null ? entityId : 0)
                .createdAt(LocalDateTime.now())
                .build();
        auditLogRepository.save(log);
    }

    @Override
    public AuditLogResponseDto getAuditLog(Integer auditLogId) {
        AuditLog log = auditLogRepository.findById(auditLogId)
                .orElseThrow(() -> new EntityNotFoundException("Audit log not found with id: " + auditLogId));
        return systemMapper.toResponseDto(log);
    }

    @Override
    public List<AuditLogResponseDto> getAllAuditLogs() {
        return auditLogRepository.findAll().stream()
                .map(systemMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<AuditLogResponseDto> getAuditLogsByUser(Integer userId) {
        return auditLogRepository.findByUserId(userId).stream()
                .map(systemMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<AuditLogResponseDto> getAuditLogsByEntity(String entityType, Integer entityId) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId).stream()
                .map(systemMapper::toResponseDto)
                .toList();
    }
}
