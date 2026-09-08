package com.abcbank.visitor.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.BranchResponseDto;
import com.abcbank.visitor.services.BranchService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping(path = "/api/v1/branches")
@RequiredArgsConstructor 
@RolesAllowed({"SYSTEM_ADMIN", "SECURITY_ADMIN"})
public class BranchController {
    private final BranchService branchService;

    @GetMapping 
    public ResponseEntity<List<BranchResponseDto>> brannches() {
        List<BranchResponseDto> dtoRes = branchService.getAllBranches();
        return ResponseEntity.ok(dtoRes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDto> brannch(@PathVariable Integer branchId) {
        BranchResponseDto resDto = branchService.getBranch(branchId);
        return ResponseEntity.ok(resDto);
    }
}
