package com.abcbank.visitor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.Role;
import com.abcbank.visitor.domain.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByRoleName(RoleName roleName);
}
