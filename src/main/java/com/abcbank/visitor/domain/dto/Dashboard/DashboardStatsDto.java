package com.abcbank.visitor.domain.dto.Dashboard;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private long activeVisitors;
    private long totalVisits;
    private Map<String, Long> visitsByStatus;
    private Map<String, Long> activeVisitorsByBranch;
}
