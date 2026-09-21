package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;
import com.abcbank.visitor.domain.entities.AuditLog;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemMapper {
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "branch.branch", target = "branchName")
    AuditLogResponseDto toResponseDto(AuditLog auditLog);
}
