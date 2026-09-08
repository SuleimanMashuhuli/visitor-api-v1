package com.abcbank.visitor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abcbank.visitor.domain.entities.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor,Integer> {

    @Query("SELECT v FROM Visitor v WHERE v.firstName LIKE :query OR v.lastName LIKE :query OR v.phoneNumber LIKE :query OR v.email LIKE :query OR v.idNumber LIKE :query")
    List<Visitor> search(@Param("query") String query);
}
