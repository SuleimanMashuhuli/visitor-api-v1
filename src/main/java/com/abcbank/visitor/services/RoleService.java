package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.PermissionResponseDto;
import com.abcbank.visitor.domain.dto.RoleResponseDto;
import com.abcbank.visitor.domain.enums.PermissionName;
import com.abcbank.visitor.domain.enums.RoleName;

public interface RoleService {

    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getRole(Integer roleId);
    RoleResponseDto getRoleByName(RoleName name);


    List<PermissionResponseDto> getAllPermission();
    PermissionResponseDto getPermission(Integer permissionId);
    PermissionResponseDto getPermissionByName(PermissionName name);

    RoleResponseDto assignPermission(Integer roleId, Integer permissionId);
    RoleResponseDto removePermission(Integer roleId, Integer permissionId);
}