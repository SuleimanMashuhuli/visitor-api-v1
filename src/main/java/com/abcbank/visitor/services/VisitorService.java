package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.VisitorRequestDto;
import com.abcbank.visitor.domain.dto.VisitorResponseDto;

public interface VisitorService {
    VisitorResponseDto createVisitor(VisitorRequestDto visitorRequestDto);
    List<VisitorResponseDto> getAllVisitors();
    VisitorResponseDto getVisitor(Integer visitorId);
    VisitorResponseDto updateVisitor(Integer visitorId, VisitorRequestDto visitorRequestDto);
    void deleteVisitor(Integer visitorId);
    List<VisitorResponseDto> searchVisitors(String query);

    void anonymizeVisitor(Integer visitorId);
}