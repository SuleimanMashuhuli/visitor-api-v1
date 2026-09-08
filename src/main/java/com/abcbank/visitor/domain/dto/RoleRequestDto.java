package com.abcbank.visitor.domain.dto;

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
public class RoleRequestDto {
    private RoleName roleName;
    private String description;
    private Set<Integer> permissionIds;
}
