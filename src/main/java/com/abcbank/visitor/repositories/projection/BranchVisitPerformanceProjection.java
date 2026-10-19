package com.abcbank.visitor.repositories.projection;

public interface BranchVisitPerformanceProjection {
    String getBranch();
    long getVolume();
    long getCheckedIn();
    long getInside();
    long getCheckedOut();
}
