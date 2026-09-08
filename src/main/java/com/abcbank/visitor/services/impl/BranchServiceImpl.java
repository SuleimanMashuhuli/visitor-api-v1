package com.abcbank.visitor.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.dto.BranchResponseDto;
import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.mappers.AdminMapper;
import com.abcbank.visitor.repositories.BranchRepository;
import com.abcbank.visitor.services.BranchService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final AdminMapper adminMapper;

    @Override
    public List<BranchResponseDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(adminMapper::toResponseDto)
                .toList();
    }

    @Override
    public BranchResponseDto getBranch(Integer branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));
        return adminMapper.toResponseDto(branch);
    }
}
