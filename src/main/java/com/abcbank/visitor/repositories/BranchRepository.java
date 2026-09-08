package com.abcbank.visitor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

}
