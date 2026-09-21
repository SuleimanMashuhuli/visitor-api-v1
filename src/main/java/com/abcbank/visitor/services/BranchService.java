package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.BranchResponseDto;
import com.abcbank.visitor.domain.enums.BranchName;

public interface BranchService {

    List<BranchResponseDto> getAllBranches(); 
    BranchResponseDto getBranch(BranchName branch);
}
