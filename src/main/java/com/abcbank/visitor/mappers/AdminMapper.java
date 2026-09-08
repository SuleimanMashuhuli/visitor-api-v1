package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.BranchRequestDto;
import com.abcbank.visitor.domain.dto.BranchResponseDto;
import com.abcbank.visitor.domain.dto.PermissionResponseDto;
import com.abcbank.visitor.domain.dto.RoleRequestDto;
import com.abcbank.visitor.domain.dto.RoleResponseDto;
import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.domain.entities.Permission;
import com.abcbank.visitor.domain.entities.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    
    Branch toEntity(BranchRequestDto branchRequestDto);
    
    @Mapping(source = "branch", target = "name")
    BranchResponseDto toResponseDto(Branch branch);

    Role toEntity(RoleRequestDto roleRequestDto);
    RoleResponseDto toResponseDto(Role role);

    PermissionResponseDto toResponseDto(Permission permission);
}
