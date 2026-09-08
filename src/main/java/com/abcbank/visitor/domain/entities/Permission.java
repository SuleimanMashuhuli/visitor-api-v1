package com.abcbank.visitor.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String resource;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

// VISITOR_CREATE
// VISITOR_VIEW
// VISITOR_UPDATE
// VISITOR_DELETE

// VISIT_CREATE
// VISIT_VIEW
// VISIT_CHECK_IN
// VISIT_CHECK_OUT

// USER_CREATE
// USER_UPDATE
// USER_DELETE

// AUDIT_VIEW
// REPORT_VIEW
// BRANCH_MANAGE