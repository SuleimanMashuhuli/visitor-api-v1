package com.abcbank.visitor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.domain.enums.RoleName;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByRoleName(RoleName roleName);
    long countByBranch(BranchName branch);
}
