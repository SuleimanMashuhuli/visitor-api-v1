package com.abcbank.visitor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByUser_Id(Integer userId);
    List<AuditLog> findByEntityType(String entityType);
    List<AuditLog> findByBranch_Id(Integer branchId);
}