package com.abcbank.visitor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByName(String name);
}
