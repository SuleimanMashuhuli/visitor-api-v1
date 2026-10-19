package com.abcbank.visitor.repositories.projection;

public interface BranchVisitCountProjection {
    String getBranch();
    long getVisits();
}