package com.abcbank.visitor.domain.dto.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CheckInTrendDto {

    private LocalDate date;
    private long checkIns;
}