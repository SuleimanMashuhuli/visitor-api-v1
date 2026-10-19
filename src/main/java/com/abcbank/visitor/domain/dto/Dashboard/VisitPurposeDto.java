package com.abcbank.visitor.domain.dto.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitPurposeDto {

    private String purpose;
    private long visits;
}