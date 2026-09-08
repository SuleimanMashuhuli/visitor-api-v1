package com.abcbank.visitor.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.PermissionResponseDto;
import com.abcbank.visitor.domain.dto.RoleResponseDto;
import com.abcbank.visitor.domain.enums.PermissionName;
import com.abcbank.visitor.domain.enums.RoleName;
import com.abcbank.visitor.services.RoleService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping(path = "/api/v1/roles")
@RequiredArgsConstructor 
@RolesAllowed({"SYSTEM_ADMIN", "SECURITY_ADMIN"})
public class RoleController {
    private final RoleService roleService;

    @GetMapping 
    public ResponseEntity<List<RoleResponseDto>> getAll() {
        List<RoleResponseDto> listRole = roleService.getAllRoles();
        return ResponseEntity.ok(listRole);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> get(@PathVariable Integer roleId) {
        RoleResponseDto role = roleService.getRole(roleId);
        return  ResponseEntity.ok(role);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponseDto> getName(@RequestParam RoleName name) {
        RoleResponseDto res = roleService.getRoleByName(name);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionResponseDto>> permisions() {
        List<PermissionResponseDto> permit = roleService.getAllPermission();
        return ResponseEntity.ok(permit);
    }

    @GetMapping("/permission/{id}")
    public ResponseEntity<PermissionResponseDto> getPermit(@PathVariable Integer permissionId) {
        PermissionResponseDto res = roleService.getPermission(permissionId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/permission/{permission}")
    public ResponseEntity<PermissionResponseDto> getPermisionName(@RequestParam PermissionName name) {
        PermissionResponseDto res = roleService.getPermissionByName(name);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/permissions/{permissionId}")
    public ResponseEntity<RoleResponseDto> give(@PathVariable Integer roleId,@PathVariable Integer permissionId) {
        RoleResponseDto res = roleService.assignPermission(roleId, permissionId);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}/permissions/{permissionId}")
    public ResponseEntity<RoleResponseDto> revert(@PathVariable Integer roleId,@PathVariable Integer permissionId) {
        RoleResponseDto dto = roleService.removePermission(roleId, permissionId);
        return ResponseEntity.ok(dto);
    }
}