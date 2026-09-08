package com.abcbank.visitor.services.impl;

import com.abcbank.visitor.domain.dto.VisitorRequestDto;
import com.abcbank.visitor.domain.dto.VisitorResponseDto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.entities.Visitor;
import com.abcbank.visitor.mappers.VisitorMapper;
import com.abcbank.visitor.repositories.VisitorRepository;
import com.abcbank.visitor.services.VisitorService;
import com.abcbank.visitor.utils.MaskingUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;

    @Override
    public VisitorResponseDto createVisitor(VisitorRequestDto visitorRequestDto) {
        Visitor visitor = visitorMapper.toEntity(visitorRequestDto);
        Visitor saveVisitor = visitorRepository.save(visitor);
        return visitorMapper.toResponseDto(saveVisitor);
    }

    @Override
    public List<VisitorResponseDto> getAllVisitors() {
        List<Visitor> visitors = visitorRepository.findAll();
        return visitors
                .stream()
                .map(visitorMapper::toResponseDto)
                .map(this::maskSensitiveData)
                .toList();
    }

    @Override
    public VisitorResponseDto getVisitor(Integer visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> 
                        new EntityNotFoundException(
                            "Visitor does not exits with ID: " + visitorId
                        ));
        return visitorMapper
                .toResponseDto(visitor);
    }

    @Override
    public VisitorResponseDto updateVisitor(Integer visitorId, VisitorRequestDto visitorRequestDto) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Visitor does not exist with ID: " + visitorId
                    ));
        visitorMapper.updateEntity(visitorRequestDto, visitor);
        Visitor saved = visitorRepository.save(visitor);
        return visitorMapper
                .toResponseDto(saved);
    }

    @Override
    public void deleteVisitor(Integer visitorId) {
        if (!visitorRepository.existsById(visitorId)) {
            throw new EntityNotFoundException("Visitor not found with id: " + visitorId);
        }
        visitorRepository.deleteById(visitorId);
    }

    @Override
    public List<VisitorResponseDto> searchVisitors(String query) {
        return visitorMapper.toResponseDtoList(visitorRepository.search("%" + query + "%"))
                .stream()
                .map(this::maskSensitiveData)
                .toList();
    }

    @Override
    public void anonymizeVisitor(Integer visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new EntityNotFoundException("Visitor not found with id: " + visitorId));
        visitor.setFirstName(null);
        visitor.setLastName(null);
        visitor.setEmail(null);
        visitor.setPhoneNumber(null);
        visitor.setIdType(null);
        visitor.setIdNumber(null);
        visitorRepository.save(visitor);
    }

    private VisitorResponseDto maskSensitiveData(VisitorResponseDto dto) {
        dto.setPhoneNumber(MaskingUtil.mask(dto.getPhoneNumber()));
        dto.setIdNumber(MaskingUtil.mask(dto.getIdNumber()));
        return dto;
    }
}
