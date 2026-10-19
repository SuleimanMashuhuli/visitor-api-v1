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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.VisitRequestDto;
import com.abcbank.visitor.domain.dto.VisitResponseDto;
import com.abcbank.visitor.services.VisitService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping(path = "/api/v1/visits")
@RequiredArgsConstructor 
@RolesAllowed({"ADMIN", "USER", "SUPER_ADMIN"})
@Slf4j 
public class VisitController {
    private final VisitService visitService;

    @PostMapping 
    public ResponseEntity<VisitResponseDto> create(@Valid @RequestBody VisitRequestDto visitRequestDto) {
        log.info("=============================== Creating-VisitRequestDto: {}", visitRequestDto);
        VisitResponseDto response = visitService.createVisit(visitRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping 
    public ResponseEntity<List<VisitResponseDto>> getAll() {
        List<VisitResponseDto> dto = visitService.getAllVisits();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<VisitResponseDto> get(@PathVariable Integer visitId) {
        VisitResponseDto res = visitService.getVisit(visitId);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<VisitResponseDto> update(@Valid @RequestBody  VisitRequestDto visitRequestDto, @PathVariable Integer visitId) {
        log.info("=============================== Updating-VisitRequestDto: {}", visitRequestDto);
        VisitResponseDto dtoRes = visitService.updateVisit(visitId, visitRequestDto);
        return ResponseEntity.ok(dtoRes);
    }

    @PatchMapping("/{visitId}/check-in")
    public ResponseEntity<VisitResponseDto> check(@PathVariable Integer visitId) {
        log.info("=============================== Checking-In: {}", visitId);
        VisitResponseDto resDto = visitService.checkIn(visitId);
        return ResponseEntity.ok(resDto);
    }

    @PatchMapping("/{visitId}/check-out")
    public ResponseEntity<VisitResponseDto> out(@PathVariable Integer visitId) {
        VisitResponseDto resDto = visitService.checkOut(visitId);
        return ResponseEntity.ok(resDto);
    }

    @GetMapping("/active")
    public ResponseEntity<List<VisitResponseDto>> getActive() {
        return ResponseEntity.ok(visitService.getActiveVisits());
    }
    @PatchMapping("/{visitId}/approve")
    public ResponseEntity<VisitResponseDto> approve(@PathVariable Integer visitId,  @RequestParam String token) {
        return ResponseEntity.ok(visitService.approveVisit(visitId, token)
        );
    }
    @PatchMapping("/{visitId}/reject")
    public ResponseEntity<VisitResponseDto> reject(@PathVariable Integer visitId,  @RequestParam String token) {
        return ResponseEntity.ok(visitService.rejectVisit(visitId, token)
        );
    }

    @PostMapping("/invite")
    public ResponseEntity<VisitResponseDto> inviteVisitor(@RequestBody @Valid VisitRequestDto visitRequestDto) {
        return ResponseEntity.ok(visitService.inviteVisitor(visitRequestDto));
    }
 }
