package com.abcbank.visitor.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.dto.BranchResponseDto;
import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.mappers.AdminMapper;
import com.abcbank.visitor.repositories.BranchRepository;
import com.abcbank.visitor.repositories.UserRepository;
import com.abcbank.visitor.services.BranchService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final AdminMapper adminMapper;
    private final UserRepository userRepository;

    @Override
    public List<BranchResponseDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branch -> {
                    BranchResponseDto dto = adminMapper.toResponseDto(branch);
                    dto.setUsers(userRepository.countByBranch(branch.getBranch()));
                    dto.setActive(branch.isActive());

                    return dto;
                })
                .toList();
    }

    @Override
    public BranchResponseDto getBranch(BranchName branchName) {
        Branch branch = branchRepository.findByBranch(branchName)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchName));
            
        BranchResponseDto dto = adminMapper.toResponseDto(branch);

        dto.setUsers(userRepository.countByBranch(branch.getBranch()));
        dto.setActive(branch.isActive());

        return dto;
    }
}
