package com.abcbank.visitor.repositories.projection;

import java.time.LocalDateTime;

import com.abcbank.visitor.domain.enums.VisitStatus;

public interface RecentCheckInProjection {
    String getVisitorIdNumber();
    String getVisitorName();
    String getHostName();
    String getBranch();
    VisitStatus getStatus();
    LocalDateTime getCheckInAt();
}
