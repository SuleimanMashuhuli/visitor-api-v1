package com.abcbank.visitor.controllers;

import com.abcbank.visitor.domain.dto.Dashboard.DashboardSummaryDto;
import com.abcbank.visitor.domain.dto.Dashboard.RecentCheckInDto;
import com.abcbank.visitor.domain.dto.Dashboard.VisitPurposeDto;
import com.abcbank.visitor.domain.dto.Dashboard.AverageBranchVisitDto;
import com.abcbank.visitor.domain.dto.Dashboard.BranchVisitPerformanceDto;
import com.abcbank.visitor.domain.dto.Dashboard.CheckInTrendDto;
import com.abcbank.visitor.services.Dashboard.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/branch-performance")
    public ResponseEntity<List<BranchVisitPerformanceDto>> getBranchPerformance() {
        return ResponseEntity.ok(dashboardService.getBranchVisitPerformance());
    }
    
    @GetMapping("/average-visits-by-branch")
    public ResponseEntity<List<AverageBranchVisitDto>> getAverageVisitsByBranch(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(dashboardService.getAverageVisitsByBranch(from, to));
    }

    @GetMapping("/check-in-trends")
    public ResponseEntity<List<CheckInTrendDto>> getCheckInTrends(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(dashboardService.getCheckInTrends(from, to));
    }
    
    @GetMapping("/recent-check-ins")
    public ResponseEntity<List<RecentCheckInDto>> getRecentCheckIns() {
        return ResponseEntity.ok(dashboardService.getRecentCheckIns());
    }

    @GetMapping("/visit-purposes")
    public ResponseEntity<List<VisitPurposeDto>> getVisitPurposes() {
        return ResponseEntity.ok(dashboardService.getVisitPurposes());
    }
}