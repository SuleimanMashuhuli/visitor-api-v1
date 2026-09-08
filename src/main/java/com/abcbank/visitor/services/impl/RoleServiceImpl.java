package com.abcbank.visitor.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.dto.PermissionResponseDto;
import com.abcbank.visitor.domain.dto.RoleResponseDto;
import com.abcbank.visitor.domain.entities.Permission;
import com.abcbank.visitor.domain.entities.Role;
import com.abcbank.visitor.domain.enums.PermissionName;
import com.abcbank.visitor.domain.enums.RoleName;
import com.abcbank.visitor.mappers.AdminMapper;
import com.abcbank.visitor.repositories.PermissionRepository;
import com.abcbank.visitor.repositories.RoleRepository;
import com.abcbank.visitor.services.RoleService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RoleServiceImpl implements RoleService {

        private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final AdminMapper adminMapper;

    @Override
    public List<RoleResponseDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(adminMapper::toResponseDto)
                .toList();
    }

    @Override
    public RoleResponseDto getRole(Integer id) {
        return adminMapper.toResponseDto(findRole(id));
    }

    @Override
    public RoleResponseDto getRoleByName(RoleName name) {
        Role role = roleRepository.findByRoleName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with name: " + name));
        return adminMapper.toResponseDto(role);
    }

    @Override
    public List<PermissionResponseDto> getAllPermission() {
        return permissionRepository.findAll().stream()
                .map(adminMapper::toResponseDto)
                .toList();
    }

    @Override
    public PermissionResponseDto getPermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        return adminMapper.toResponseDto(permission);
    }

    @Override
    public PermissionResponseDto getPermissionByName(PermissionName name) {
        Permission permission = permissionRepository.findByName(name.name())
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with name: " + name));
        return adminMapper.toResponseDto(permission);
    }

    @Override
    public RoleResponseDto assignPermission(Integer roleId, Integer permissionId) {
        Role role = findRole(roleId);
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + permissionId));
        role.getPermissions().add(permission);
        return adminMapper.toResponseDto(roleRepository.save(role));
    }

    @Override
    public RoleResponseDto removePermission(Integer roleId, Integer permissionId) {
        Role role = findRole(roleId);
        role.getPermissions().removeIf(p -> p.getId() == permissionId);
        return adminMapper.toResponseDto(roleRepository.save(role));
    }

    private Role findRole(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }
}
