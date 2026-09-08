package com.abcbank.visitor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByUserId(Integer userId);
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, Integer entityId);
}
