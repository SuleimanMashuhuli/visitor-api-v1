package com.abcbank.visitor.repositories.projection;

import java.time.LocalDate;

public interface CheckInTrendProjection {
    LocalDate getDate();
    long getCheckIns();
}
