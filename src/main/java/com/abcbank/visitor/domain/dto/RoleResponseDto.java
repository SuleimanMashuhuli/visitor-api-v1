package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.abcbank.visitor.domain.enums.RoleName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
    private int id;
    private RoleName roleName;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<PermissionResponseDto> permissions;
}
