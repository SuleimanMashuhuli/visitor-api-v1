package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.BranchResponseDto;

public interface BranchService {

    List<BranchResponseDto> getAllBranches(); 
    BranchResponseDto getBranch(Integer branchId);
}
