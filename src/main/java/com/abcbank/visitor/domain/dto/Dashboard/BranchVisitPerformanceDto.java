package com.abcbank.visitor.domain.dto.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BranchVisitPerformanceDto {

    private String branch;
    private long volume;
    private double checkedInPercentage;
    private double insidePercentage;
    private double checkedOutPercentage;
}
