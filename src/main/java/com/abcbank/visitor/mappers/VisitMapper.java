package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.VisitRequestDto;
import com.abcbank.visitor.domain.dto.VisitResponseDto;
import com.abcbank.visitor.domain.entities.Visit;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitMapper {

    Visit toEntity(VisitRequestDto visitRequestDto);
    void updateEntity(VisitRequestDto visitRequestDto, @MappingTarget Visit visit);

    @Mapping(source = "visitor.id", target = "visitorId")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.branch", target = "branchName")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "createdBy.username", target = "createdByUsername")
    VisitResponseDto toResponseDto(Visit visit);
}
