package com.abcbank.visitor.services.Dashboard;

import java.time.LocalDate;
import java.util.List;

import com.abcbank.visitor.domain.dto.Dashboard.AverageBranchVisitDto;
import com.abcbank.visitor.domain.dto.Dashboard.BranchVisitPerformanceDto;
import com.abcbank.visitor.domain.dto.Dashboard.CheckInTrendDto;
import com.abcbank.visitor.domain.dto.Dashboard.DashboardSummaryDto;
import com.abcbank.visitor.domain.dto.Dashboard.RecentCheckInDto;
import com.abcbank.visitor.domain.dto.Dashboard.VisitPurposeDto;

public interface DashboardService {
    DashboardSummaryDto getSummary();
    List<BranchVisitPerformanceDto> getBranchVisitPerformance();
    List<AverageBranchVisitDto> getAverageVisitsByBranch(LocalDate from, LocalDate to);
    List<CheckInTrendDto> getCheckInTrends(LocalDate from, LocalDate to);
    List<RecentCheckInDto> getRecentCheckIns();
    List<VisitPurposeDto> getVisitPurposes();
}
