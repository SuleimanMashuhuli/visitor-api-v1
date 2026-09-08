package com.abcbank.visitor.services.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.dto.Dashboard.DashboardStatsDto;
import com.abcbank.visitor.domain.enums.VisitStatus;
import com.abcbank.visitor.repositories.VisitRepository;
import com.abcbank.visitor.services.Dashboard.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final VisitRepository visitRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getStats() {
        long activeVisitors = visitRepository.countByStatus(VisitStatus.CHECKED_IN)
                + visitRepository.countByStatus(VisitStatus.INSIDE);
        long totalVisits = visitRepository.count();

        Map<String, Long> visitsByStatus = new HashMap<>();
        for (Object[] row : visitRepository.countByStatusGrouped()) {
            visitsByStatus.put(((VisitStatus) row[0]).name(), (Long) row[1]);
        }

        Map<String, Long> activeByBranch = new HashMap<>();
        for (Object[] row : visitRepository.countActiveGroupedByBranch(
                List.of(VisitStatus.CHECKED_IN, VisitStatus.INSIDE))) {
            activeByBranch.put(String.valueOf(row[0]), (Long) row[1]);
        }

        return DashboardStatsDto.builder()
                .activeVisitors(activeVisitors)
                .totalVisits(totalVisits)
                .visitsByStatus(visitsByStatus)
                .activeVisitorsByBranch(activeByBranch)
                .build();
    }
}