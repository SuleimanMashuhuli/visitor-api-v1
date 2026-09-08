package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.AuditLogResponseDto;
import com.abcbank.visitor.domain.dto.NotificationResponseDto;
import com.abcbank.visitor.domain.entities.AuditLog;
import com.abcbank.visitor.domain.entities.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemMapper {
    
    @Mapping(source = "visit.id", target = "visitId")
    NotificationResponseDto toResponseDto(Notification notification);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "branch.id", target = "branchId")
    AuditLogResponseDto toResponseDto(AuditLog auditLog);
}
