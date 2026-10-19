package com.abcbank.visitor.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.dto.VisitRequestDto;
import com.abcbank.visitor.domain.dto.VisitResponseDto;
import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.domain.entities.Visit;
import com.abcbank.visitor.domain.entities.Visitor;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.domain.enums.VisitStatus;
import com.abcbank.visitor.mappers.VisitMapper;
import com.abcbank.visitor.repositories.BranchRepository;
import com.abcbank.visitor.repositories.VisitRepository;
import com.abcbank.visitor.repositories.VisitorRepository;
import com.abcbank.visitor.services.AppNotification;
import com.abcbank.visitor.services.AuditLogService;
import com.abcbank.visitor.services.VisitService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j 
public class VisitServiceImpl implements VisitService{
    private final VisitMapper visitMapper;
    private final VisitRepository visitRepository;
    private final AuditLogService auditLogService;
    private final VisitorRepository visitorRepository;
    private final AppNotification appNotification;
    private final BranchRepository branchRepository;

    @Override
    public VisitResponseDto createVisit(VisitRequestDto visitRequestDto) {
        Visitor visitor = resolveVisitor(visitRequestDto);
        Branch branch = resolveBranch(visitRequestDto);
        Visit visit = visitMapper.toEntity(visitRequestDto);
        visit.setVisitor(visitor);
        visit.setBranch(branch);
        visit.setStatus(VisitStatus.PENDING);
        visit.setHostActionToken(UUID.randomUUID().toString());
        visit.setHostActionTokenExpiresAt(LocalDateTime.now().plusHours(24));
        Visit saveVisit = visitRepository.save(visit);
        auditLogService.recordEvent("VISIT_CREATE", "Visit scheduled", "VISIT");
        try {
            appNotification.sendVisitNotification(saveVisit);
        } catch (Exception e) {
            log.info("Failed to send visit notification for visit {}", saveVisit.getId());
        }
        return visitMapper.toResponseDto(saveVisit);
    }

    private Visitor resolveVisitor(VisitRequestDto visitRequestDto) {
        if (visitRequestDto.getVisitorId() != 0) {
            return visitorRepository.findById(visitRequestDto.getVisitorId())
                    .orElseThrow(() -> new EntityNotFoundException("Visitor not found with id: " + visitRequestDto.getVisitorId()));
        }
        if (visitRequestDto.getIdNumber() != null && !visitRequestDto.getIdNumber().isBlank()) {
            return visitorRepository.findByIdNumber(visitRequestDto.getIdNumber())
                    .orElseGet(() -> createVisitor(visitRequestDto));
        }
        if (visitRequestDto.getEmail() != null && !visitRequestDto.getEmail().isBlank()) {
            return visitorRepository.findByEmail(visitRequestDto.getEmail())
                    .orElseGet(() -> createVisitor(visitRequestDto));
        }
        return createVisitor(visitRequestDto);
    }

    private Visitor createVisitor(VisitRequestDto visitRequestDto) {
        Visitor visitor = Visitor.builder()
                .firstName(visitRequestDto.getFirstName())
                .lastName(visitRequestDto.getLastName())
                .email(visitRequestDto.getEmail())
                .phoneNumber(visitRequestDto.getPhoneNumber())
                .idType(visitRequestDto.getIdType())
                .idNumber(visitRequestDto.getIdNumber())
                .organization(visitRequestDto.getOrganization())
                .build();
        return visitorRepository.save(visitor);
    }

    @Override 
    public VisitResponseDto getVisit(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> 
                        new EntityNotFoundException("Visit does not exits with ID: " + visitId));
        return visitMapper.toResponseDto(visit);
    }

    @Override 
    public List<VisitResponseDto> getAllVisits() {
        List<Visit> visits = visitRepository.findAll();
        return visits.stream()
                .map(visitMapper::toResponseDto)
                .toList();
    }

    @Override 
    public VisitResponseDto updateVisit(Integer visitId, VisitRequestDto visitRequestDto) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException("Visit does not exist with ID: " + visitId));
        visitMapper.updateEntity(visitRequestDto, visit);
        Visit saved = visitRepository.save(visit);
        return visitMapper.toResponseDto(saved);
    }

    @Override 
    public VisitResponseDto checkIn(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException("Visit not found: " + visitId));
        if(visit.getStatus() != VisitStatus.APPROVED) {
            throw new IllegalStateException("Only an approved visit can be checked in");
        }
        visit.setStatus(VisitStatus.CHECKED_IN);
        visit.setCheckInAt(LocalDateTime.now());
        Visit saved = visitRepository.save(visit);
        auditLogService.recordEvent("VISIT_CHECK_IN", "Visitor checked in", "VISIT");
        return visitMapper.toResponseDto(saved);
    }

    @Override 
    public VisitResponseDto checkOut(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException("Visit not found: " + visitId));
        if(visit.getStatus() != VisitStatus.CHECKED_IN) {
            throw new IllegalStateException("Only a checked-in visit can be checked out");
        }
        visit.setStatus(VisitStatus.CHECKED_OUT);
        visit.setCheckOutAt(LocalDateTime.now());
        Visit saved = visitRepository.save(visit);
        auditLogService.recordEvent("VISIT_CHECK_OUT", "Visitor checked out", "VISIT");
        return  visitMapper.toResponseDto(saved);
    }

    @Override
    public List<VisitResponseDto> getActiveVisits() {
        return visitRepository.findByStatusIn(List.of(VisitStatus.CHECKED_IN, VisitStatus.INSIDE))
                .stream()
                .map(visitMapper::toResponseDto)
                .toList();
    }

    @Override
    public VisitResponseDto approveVisit(Integer visitId, String token) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Visit not found: " + visitId));
        validateHostAction(visit, token);
        if (visit.getStatus() != VisitStatus.PENDING) {
            throw new IllegalStateException("Only a pending visit can be approved");
        }
        visit.setStatus(VisitStatus.APPROVED);
        Visit saved = visitRepository.save(visit);
        auditLogService.recordEvent("VISIT_APPROVED", "Visit approved by host", "VISIT");
        return visitMapper.toResponseDto(saved);
    }

    @Override
    public VisitResponseDto rejectVisit(Integer visitId, String token) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Visit not found: " + visitId));
        validateHostAction(visit, token);
        if (visit.getStatus() != VisitStatus.PENDING) {
            throw new IllegalStateException("Only a pending visit can be rejected");
        }
        visit.setStatus(VisitStatus.REJECTED);
        Visit saved = visitRepository.save(visit);
        auditLogService.recordEvent("VISIT_REJECTED", "Visit rejected by host", "VISIT");
        return visitMapper.toResponseDto(saved);
    }

    private void validateHostAction(Visit visit, String token) {
        if (token == null || !token.equals(visit.getHostActionToken())) {
            throw new IllegalArgumentException("Invalid host action token");
        }
        if (visit.getHostActionTokenExpiresAt() == null || visit.getHostActionTokenExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Host action link has expired");
        }
    }

    @Override
    public VisitResponseDto inviteVisitor(VisitRequestDto visitRequestDto) {
        Visitor visitor = resolveVisitor(visitRequestDto);
        Branch branch = resolveBranch(visitRequestDto);
        Visit visit = visitMapper.toEntity(visitRequestDto);
        visit.setVisitor(visitor);
        visit.setBranch(branch);
        visit.setStatus(VisitStatus.EXPECTED);
        Visit savedVisit = visitRepository.save(visit);
        auditLogService.recordEvent("VISITOR_INVITED", "Host invited visitor", "VISIT");
        return visitMapper.toResponseDto(savedVisit);
    }

    private Branch resolveBranch(VisitRequestDto request) {
        if (request.getBranchName() == null || request.getBranchName().isBlank()) {
            throw new IllegalArgumentException("Branch name is required");
        }

        String branchName = request.getBranchName().trim();

        BranchName branch;
        try {
            branch = BranchName.valueOf(branchName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid branch name: " + branchName);
        }

        return branchRepository.findByBranch(branch)
                .orElseThrow(() ->
                        new EntityNotFoundException("Branch not found: " + branchName)
                );
    }
}
