package com.abcbank.visitor.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abcbank.visitor.domain.entities.Visit;
import com.abcbank.visitor.domain.enums.VisitStatus;
import com.abcbank.visitor.repositories.projection.BranchVisitCountProjection;
import com.abcbank.visitor.repositories.projection.BranchVisitPerformanceProjection;
import com.abcbank.visitor.repositories.projection.CheckInTrendProjection;
import com.abcbank.visitor.repositories.projection.RecentCheckInProjection;
import com.abcbank.visitor.repositories.projection.VisitPurposeProjection;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    long countByStatus(VisitStatus status);
    @Query(value = """
        SELECT v.status, COUNT(*)
        FROM visits v
        GROUP BY v.status
        """, nativeQuery = true)
    List<Object[]> countByStatusGrouped();

    @Query(value = """
        SELECT b.branch, COUNT(*)
        FROM visits v
        JOIN branches b ON v.branch = b.id
        WHERE v.status IN (:statuses)
        GROUP BY b.branch
        """, nativeQuery = true)
    List<Object[]> countActiveGroupedByBranch(@Param("statuses") List<String> statuses);
    List<Visit> findByStatusIn(List<VisitStatus> statuses);

    @Query(value = """
        SELECT
            b.branch AS branch,
            COUNT(*) AS volume,
            SUM(CASE WHEN v.status = 'CHECKED_IN' THEN 1 ELSE 0 END) AS "checkedIn",
            SUM(CASE WHEN v.status = 'INSIDE' THEN 1 ELSE 0 END) AS inside,
            SUM(CASE WHEN v.status = 'CHECKED_OUT' THEN 1 ELSE 0 END) AS "checkedOut"
        FROM visits v
        JOIN branches b ON v.branch = b.id
        GROUP BY b.branch
        ORDER BY COUNT(*) DESC
        """, nativeQuery = true)
    List<BranchVisitPerformanceProjection> getBranchVisitPerformance();

    @Query(value = """
        SELECT b.branch AS branch, COUNT(*) AS visits
        FROM visits v
        JOIN branches b ON v.branch = b.id
        WHERE v.scheduled_at >= :from AND v.scheduled_at < :to
        GROUP BY b.branch
        ORDER BY COUNT(*) DESC
        """, nativeQuery = true)
    List<BranchVisitCountProjection> getBranchVisitCounts(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query(value = """
        SELECT DATE(v.check_in_at) AS date, COUNT(*) AS check_ins
        FROM visits v
        WHERE v.check_in_at IS NOT NULL AND v.check_in_at >= :from AND v.check_in_at < :to
        GROUP BY DATE(v.check_in_at)
        ORDER BY DATE(v.check_in_at)
        """, nativeQuery = true)
    List<CheckInTrendProjection> getCheckInTrends(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query(value = """
        SELECT
            visitor.id_number AS "visitorIdNumber",
            CONCAT(visitor.first_name, ' ', visitor.last_name) AS "visitorName",
            v.host_name AS "hostName",
            b.branch AS branch,
            v.status AS status,
            v.check_in_at AS "checkInAt"
        FROM visits v
        JOIN visitors visitor ON v.visitor_id = visitor.id
        JOIN branches b ON v.branch = b.id
        WHERE v.check_in_at IS NOT NULL
        ORDER BY v.check_in_at DESC
        """, nativeQuery = true)
    List<RecentCheckInProjection> getRecentCheckIns(Pageable pageable);

    @Query(value = """
        SELECT v.purpose AS purpose, COUNT(*) AS visits
        FROM visits v
        WHERE v.purpose IS NOT NULL AND TRIM(v.purpose) <> ''
        GROUP BY v.purpose
        ORDER BY COUNT(*) DESC
        """, nativeQuery = true)
    List<VisitPurposeProjection> getVisitPurposes();
}