package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;
import com.abcbank.visitor.domain.entities.AuditLog;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.roleName", target = "operatorLevel")
    @Mapping(source = "branch.branch", target = "branch")
    AuditLogResponseDto toResponseDto(AuditLog auditLog);
}