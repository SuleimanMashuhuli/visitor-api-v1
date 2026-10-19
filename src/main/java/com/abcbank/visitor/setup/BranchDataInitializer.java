package com.abcbank.visitor.setup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.repositories.BranchRepository;

@Component
@RequiredArgsConstructor
public class BranchDataInitializer implements CommandLineRunner {

    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public void run(String... args) {
        for (BranchName branchName : BranchName.values()) {
            if (!branchRepository.existsByBranch(branchName)) {
                branchRepository.save(
                    Branch.builder()
                        .branch(branchName)
                        .active(true)
                        .build()
                );
            }
        }
    }
}