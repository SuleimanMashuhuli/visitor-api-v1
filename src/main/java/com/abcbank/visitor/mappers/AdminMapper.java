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
    
    @Mapping(target = "id", source = "id")
    @Mapping(source = "branch", target = "name")
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "active", ignore = true)
    BranchResponseDto toResponseDto(Branch branch);
}
