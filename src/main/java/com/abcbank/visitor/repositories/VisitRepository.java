package com.abcbank.visitor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abcbank.visitor.domain.entities.Visit;
import com.abcbank.visitor.domain.enums.VisitStatus;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    long countByStatus(VisitStatus status);

    @Query("SELECT v.status, COUNT(v) FROM Visit v GROUP BY v.status")
    List<Object[]> countByStatusGrouped();

    @Query("SELECT v.branch.branch, COUNT(v) FROM Visit v WHERE v.status IN :statuses GROUP BY v.branch.branch")
    List<Object[]> countActiveGroupedByBranch(@Param("statuses") List<VisitStatus> statuses);

    List<Visit> findByStatusIn(List<VisitStatus> statuses);
}
