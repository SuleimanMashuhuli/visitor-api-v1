package com.abcbank.visitor.services.impl;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;
import com.abcbank.visitor.domain.entities.AuditLog;
import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.mappers.SystemMapper;
import com.abcbank.visitor.repositories.AuditLogRepository;
import com.abcbank.visitor.repositories.UserRepository;
import com.abcbank.visitor.services.AuditLogService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final SystemMapper systemMapper;
    private final UserRepository userRepository;

    @Override
    public void recordEvent(String action, String description, String entityType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found: " + username));
        }

        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .description(description)
                .entityType(entityType)
                .user(user)
                .ipAddress(getClientIpAddress())
                .build();
        auditLogRepository.save(auditLog);
    }

    @Override
    public AuditLogResponseDto getAuditLog(Integer auditLogId) {
        AuditLog auditLog = auditLogRepository.findById(auditLogId)
                .orElseThrow(() -> new EntityNotFoundException("Audit log not found with ID: " + auditLogId ));

        return systemMapper.toResponseDto(auditLog);
    }

    @Override
    public List<AuditLogResponseDto> getAllAuditLogs() {
        return auditLogRepository.findAll().stream().map(systemMapper::toResponseDto).toList();
    }

    @Override
    public List<AuditLogResponseDto> getAuditLogsByUser(Integer userId) {
        return auditLogRepository.findByUser_Id(userId)
                .stream().map(systemMapper::toResponseDto).toList();
    }

    @Override
    public List<AuditLogResponseDto> getAuditLogsByEntity(String entityType) {
        return auditLogRepository.findByEntityType(entityType).stream().map(systemMapper::toResponseDto).toList();
    }

    @Override
    public List<AuditLogResponseDto> getAuditLogsByBranch(Integer branchId) {
        return auditLogRepository.findByBranch_Id(branchId).stream().map(systemMapper::toResponseDto).toList();
    }

    private String getClientIpAddress() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof ServletRequestAttributes servletAttributes)) {
            return null;
        }
        HttpServletRequest request = servletAttributes.getRequest();
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
