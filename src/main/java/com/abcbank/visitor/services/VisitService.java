package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.VisitRequestDto;
import com.abcbank.visitor.domain.dto.VisitResponseDto;

public interface VisitService {
    VisitResponseDto createVisit(VisitRequestDto visitRequestDto);
    VisitResponseDto getVisit(Integer visitId);
    List<VisitResponseDto> getAllVisits();
    VisitResponseDto updateVisit(Integer visitId, VisitRequestDto visitRequestDto);
    VisitResponseDto approveVisit(Integer visitId);
    VisitResponseDto rejectVisit(Integer visitId);
    VisitResponseDto checkIn(Integer visitId);
    VisitResponseDto checkOut(Integer visitId);
    List<VisitResponseDto> getActiveVisits();
}

