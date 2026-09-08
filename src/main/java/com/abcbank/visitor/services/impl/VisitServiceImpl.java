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

    @Override
    public VisitResponseDto createVisit(VisitRequestDto visitRequestDto) {
        Visit visit = visitMapper.toEntity(visitRequestDto);
        Visit saveVisit = visitRepository.save(visit);
        return visitMapper.toResponseDto(saveVisit);
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
    public VisitResponseDto approveVisit(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException("Visit by this ID has not been approved: " + visitId));
        visit.setStatus(VisitStatus.APPROVED);
        Visit saved = visitRepository.save(visit);
        return  visitMapper.toResponseDto(saved);
    }

    @Override 
    public VisitResponseDto rejectVisit(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Visit by this ID has not been rejected: " + visitId
                    ));
        visit.setStatus(VisitStatus.REJECTED);
        Visit saved = visitRepository.save(visit);
        return  visitMapper.toResponseDto(saved);
    }

    @Override 
    public VisitResponseDto checkIn(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Visit by this ID has not been checked-in: " + visitId
                    ));
        if(visit.getStatus() != VisitStatus.APPROVED) {
            throw new IllegalStateException(
                "Only an approved visit can be checked in"
            );
        }
        visit.setStatus(VisitStatus.CHECKED_IN);
        visit.setCheckInAt(LocalDateTime.now());
        Visit saved = visitRepository.save(visit);
        return  visitMapper.toResponseDto(saved);
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
