package com.abcbank.visitor.domain.dto.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {

    private long totalVisits;
    private long checkedInVisits;
    private long insideVisits;
    private long checkedOutVisits;
}