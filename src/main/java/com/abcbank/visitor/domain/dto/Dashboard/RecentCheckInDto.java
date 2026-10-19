package com.abcbank.visitor.domain.dto.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

import com.abcbank.visitor.domain.enums.VisitStatus;

@Data
@AllArgsConstructor
public class RecentCheckInDto {

    private String visitorIdNumber;
    private String visitorName;
    private String hostName;
    private String branch;
    private VisitStatus status;
    private LocalDateTime checkInAt;
}