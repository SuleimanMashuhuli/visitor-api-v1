package com.abcbank.visitor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.Dashboard.DashboardStatsDto;
import com.abcbank.visitor.services.Dashboard.DashboardService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@RolesAllowed({"ADMIN", "USER"})
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
}
