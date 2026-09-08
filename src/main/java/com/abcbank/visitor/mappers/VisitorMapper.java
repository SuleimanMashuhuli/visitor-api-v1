package com.abcbank.visitor.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.VisitorRequestDto;
import com.abcbank.visitor.domain.dto.VisitorResponseDto;
import com.abcbank.visitor.domain.entities.Visitor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitorMapper {

    Visitor toEntity(VisitorRequestDto visitorRequestDto); 
    void updateEntity( VisitorRequestDto visitorRequestDto, @MappingTarget Visitor visitor ); 
    VisitorResponseDto toResponseDto(Visitor visitor); 
    List<VisitorResponseDto> toResponseDtoList(List<Visitor> visitors); 
}
