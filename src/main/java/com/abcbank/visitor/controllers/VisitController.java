package com.abcbank.visitor.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.VisitRequestDto;
import com.abcbank.visitor.domain.dto.VisitResponseDto;
import com.abcbank.visitor.services.VisitService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping(path = "/api/v1/visits")
@RequiredArgsConstructor 
@RolesAllowed({"SYSTEM_ADMIN", "SECURITY_ADMIN", "SECURITY_OFFICER"})
public class VisitController {
    private final VisitService visitService;

    @PostMapping 
    public ResponseEntity<VisitResponseDto> create(@RequestBody VisitRequestDto visitRequestDto) {
        VisitResponseDto response = visitService.createVisit(visitRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping 
    public ResponseEntity<List<VisitResponseDto>> getAll() {
        List<VisitResponseDto> dto = visitService.getAllVisits();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitResponseDto> get(@PathVariable Integer visitId) {
        VisitResponseDto res = visitService.getVisit(visitId);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitResponseDto> update(@RequestBody  VisitRequestDto visitRequestDto, @PathVariable Integer visitId) {
        VisitResponseDto dtoRes = visitService.updateVisit(visitId, visitRequestDto);
        return ResponseEntity.ok(dtoRes);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<VisitResponseDto> approve(@PathVariable Integer visitId) {
        VisitResponseDto resDto = visitService.approveVisit(visitId);
        return ResponseEntity.ok(resDto);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<VisitResponseDto> reject(@PathVariable Integer visitId) {
        VisitResponseDto resDto = visitService.rejectVisit(visitId);
        return ResponseEntity.ok(resDto);
    }

    @PatchMapping("/{id}/check-in")
    public ResponseEntity<VisitResponseDto> check(@PathVariable Integer visitId) {
        VisitResponseDto resDto = visitService.checkIn(visitId);
        return ResponseEntity.ok(resDto);
    }

    @PatchMapping("/{id}/check-out")
    public ResponseEntity<VisitResponseDto> out(@PathVariable Integer visitId) {
        VisitResponseDto resDto = visitService.checkOut(visitId);
        return ResponseEntity.ok(resDto);
    }

    @GetMapping("/active")
    public ResponseEntity<List<VisitResponseDto>> getActive() {
        return ResponseEntity.ok(visitService.getActiveVisits());
    }
 }
