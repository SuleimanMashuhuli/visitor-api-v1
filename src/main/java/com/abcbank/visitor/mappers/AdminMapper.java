package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.BranchRequestDto;
import com.abcbank.visitor.domain.dto.BranchResponseDto;
import com.abcbank.visitor.domain.entities.Branch;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    
    Branch toEntity(BranchRequestDto branchRequestDto);
    
    @Mapping(source = "branch", target = "name")
    BranchResponseDto toResponseDto(Branch branch);
}
