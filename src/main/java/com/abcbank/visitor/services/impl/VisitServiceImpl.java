package com.abcbank.visitor.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.dto.VisitRequestDto;
import com.abcbank.visitor.domain.dto.VisitResponseDto;
import com.abcbank.visitor.domain.entities.Visit;
import com.abcbank.visitor.domain.entities.Visitor;
import com.abcbank.visitor.domain.enums.VisitStatus;
import com.abcbank.visitor.mappers.VisitMapper;
import com.abcbank.visitor.repositories.VisitRepository;
import com.abcbank.visitor.repositories.VisitorRepository;
import com.abcbank.visitor.services.AuditLogService;
import com.abcbank.visitor.services.VisitService;
import com.abcbank.visitor.services.VisitorService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitServiceImpl implements VisitService{
    private final VisitMapper visitMapper;
    private final VisitRepository visitRepository;
    private final VisitorService visitorService;
    private final AuditLogService auditLogService;
    private final VisitorRepository visitorRepository;

    @Override
    public VisitResponseDto createVisit(VisitRequestDto visitRequestDto) {
        Visitor visitor = resolveVisitor(visitRequestDto);

        Visit visit = visitMapper.toEntity(visitRequestDto);
        visit.setVisitor(visitor);
        visit.setStatus(VisitStatus.PENDING);
        Visit saveVisit = visitRepository.save(visit);

        auditLogService.recordEvent("VISIT_CREATE", "Visit scheduled", "VISIT", saveVisit.getId());
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
                        new EntityNotFoundException(
                            "Visit does not exits with ID: " + visitId
                        ));
        return visitMapper
                .toResponseDto(visit);
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
                    new EntityNotFoundException(
                        "Visit does not exist with ID: " + visitId
                    ));
        visitMapper.updateEntity(visitRequestDto, visit);
        Visit saved = visitRepository.save(visit);
        return visitMapper
                .toResponseDto(saved);
    }

    @Override 
    public VisitResponseDto checkIn(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Visit not found: " + visitId
                    ));
        if(visit.getStatus() != VisitStatus.APPROVED) {
            throw new IllegalStateException(
                "Only an approved visit can be checked in"
            );
        }
        visit.setStatus(VisitStatus.CHECKED_IN);
        visit.setCheckInAt(LocalDateTime.now());
        Visit saved = visitRepository.save(visit);

        auditLogService.recordEvent("VISIT_CHECK_IN", "Visitor checked in", "VISIT", visitId);
        return visitMapper.toResponseDto(saved);
    }

    @Override 
    public VisitResponseDto checkOut(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Visit not found: " + visitId
                    ));
        if(visit.getStatus() != VisitStatus.CHECKED_IN) {
            throw new IllegalStateException(
                "Only a checked-in visit can be checked out"
            );
        }
        visit.setStatus(VisitStatus.CHECKED_OUT);
        visit.setCheckOutAt(LocalDateTime.now());
        Visit saved = visitRepository.save(visit);
            
        Visitor visitor = saved.getVisitor();
        visitorService.anonymizeVisitor(visitor.getId());

        return  visitMapper.toResponseDto(saved);
    }

    @Override
    public List<VisitResponseDto> getActiveVisits() {
        return visitRepository.findByStatusIn(List.of(VisitStatus.CHECKED_IN, VisitStatus.INSIDE))
                .stream()
                .map(visitMapper::toResponseDto)
                .toList();
    }
}
