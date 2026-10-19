package com.abcbank.visitor.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.VisitorRequestDto;
import com.abcbank.visitor.domain.dto.VisitorResponseDto;
import com.abcbank.visitor.services.VisitorService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping(path = "/api/v1/visitors")
@RequiredArgsConstructor 
@RolesAllowed({"ADMIN", "USER", "SUPER_ADMIN"})
@Slf4j 
public class VisitorController {
    private final VisitorService visitorService;

    @GetMapping 
    public ResponseEntity<List<VisitorResponseDto>> getAll() {
        List<VisitorResponseDto> response = visitorService.getAllVisitors();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{visitorId}")
    public ResponseEntity<VisitorResponseDto> get(@PathVariable Integer visitorId) {
        VisitorResponseDto responseDto = visitorService.getVisitor(visitorId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{visitorId}")
    public ResponseEntity<VisitorResponseDto> update(@PathVariable Integer visitorId, @Valid @RequestBody VisitorRequestDto visitorRequestDto) {
        log.info("=============================== Updating-VisitorRequestDto: {}", visitorRequestDto);
        VisitorResponseDto dto = visitorService.updateVisitor(visitorId, visitorRequestDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VisitorResponseDto>> search(@RequestParam String query) {
        List<VisitorResponseDto> response = visitorService.searchVisitors(query);
        return ResponseEntity.ok(response);
    }
}
