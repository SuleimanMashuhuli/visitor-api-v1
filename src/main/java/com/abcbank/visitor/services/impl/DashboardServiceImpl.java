package com.abcbank.visitor.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.dto.Dashboard.AverageBranchVisitDto;
import com.abcbank.visitor.domain.dto.Dashboard.BranchVisitPerformanceDto;
import com.abcbank.visitor.domain.dto.Dashboard.CheckInTrendDto;
import com.abcbank.visitor.domain.dto.Dashboard.DashboardSummaryDto;
import com.abcbank.visitor.domain.dto.Dashboard.RecentCheckInDto;
import com.abcbank.visitor.domain.dto.Dashboard.VisitPurposeDto;
import com.abcbank.visitor.domain.enums.VisitStatus;
import com.abcbank.visitor.repositories.VisitRepository;
import com.abcbank.visitor.services.Dashboard.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final VisitRepository visitRepository;

    @Override
    public DashboardSummaryDto getSummary() {
        return DashboardSummaryDto.builder()
                .totalVisits(visitRepository.count())
                .checkedInVisits(visitRepository.countByStatus(VisitStatus.CHECKED_IN))
                .insideVisits(visitRepository.countByStatus(VisitStatus.INSIDE))
                .checkedOutVisits(visitRepository.countByStatus(VisitStatus.CHECKED_OUT))
                .build();
    }

    @Override
    public List<BranchVisitPerformanceDto> getBranchVisitPerformance() {
        return visitRepository.getBranchVisitPerformance()
                .stream()
                .map(branch -> {
                    long volume = branch.getVolume();
                    double checkedInPercentage = percentage(branch.getCheckedIn(), volume);
                    double insidePercentage = percentage(branch.getInside(), volume);
                    double checkedOutPercentage = percentage(branch.getCheckedOut(), volume);
                    return new BranchVisitPerformanceDto(
                            branch.getBranch(),
                            volume,
                            checkedInPercentage,
                            insidePercentage,
                            checkedOutPercentage
                    );
                })
                .toList();
    }

    private double percentage(long value, long total) {
        if (total == 0) {
            return 0;
        }
        return Math.round((value * 100.0 / total) * 100.0) / 100.0;
    }

    @Override
    public List<AverageBranchVisitDto> getAverageVisitsByBranch(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.plusDays(1).atStartOfDay();
        long numberOfDays = ChronoUnit.DAYS.between(from, to) + 1;
        return visitRepository
                .getBranchVisitCounts(start, end)
                .stream()
                .map(branch -> {
                    double average = branch.getVisits() / (double) numberOfDays;
                    return new AverageBranchVisitDto(branch.getBranch(), Math.round(average * 100.0) / 100.0);
                })
                .toList();
    }

    @Override
    public List<CheckInTrendDto> getCheckInTrends(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.plusDays(1).atStartOfDay();
        return visitRepository
                .getCheckInTrends(start, end)
                .stream()
                .map(trend -> new CheckInTrendDto(
                        trend.getDate(),
                        trend.getCheckIns()
                ))
                .toList();
    }

    @Override
    public List<RecentCheckInDto> getRecentCheckIns() {
        return visitRepository
                .getRecentCheckIns(PageRequest.of(0,5))
                .stream()
                .map(checkIn -> new RecentCheckInDto(
                        checkIn.getVisitorIdNumber(),
                        checkIn.getVisitorName(),
                        checkIn.getHostName(),
                        checkIn.getBranch(),
                        checkIn.getStatus(),
                        checkIn.getCheckInAt()))
                .toList();
    }

    @Override
    public List<VisitPurposeDto> getVisitPurposes() {
        return visitRepository
                .getVisitPurposes()
                .stream()
                .map(purpose -> new VisitPurposeDto(
                        purpose.getPurpose(),
                        purpose.getVisits()
                ))
                .toList();
    }
}