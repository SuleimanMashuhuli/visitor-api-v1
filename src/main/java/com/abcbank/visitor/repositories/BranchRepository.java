package com.abcbank.visitor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.domain.enums.BranchName;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Optional<Branch> findByBranch(BranchName branch);
    boolean existsByBranch(BranchName branch);
}
