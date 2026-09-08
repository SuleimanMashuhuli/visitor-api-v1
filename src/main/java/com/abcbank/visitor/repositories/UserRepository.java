package com.abcbank.visitor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abcbank.visitor.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
