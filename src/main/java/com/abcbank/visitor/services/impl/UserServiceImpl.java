package com.abcbank.visitor.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.dto.UserRequestDto;
import com.abcbank.visitor.domain.dto.UserResponseDto;
import com.abcbank.visitor.domain.entities.Branch;
import com.abcbank.visitor.domain.entities.Role;
import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.mappers.UserMapper;
import com.abcbank.visitor.repositories.BranchRepository;
import com.abcbank.visitor.repositories.RoleRepository;
import com.abcbank.visitor.repositories.UserRepository;
import com.abcbank.visitor.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
@Transactional 
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        user.setBranch(resolveBranch(dto.getBranchId()));
        user.setRoles(resolveRoles(dto.getRoleIds()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUser(Integer userId) {
        return userMapper.toResponseDto(findUser(userId));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto updateUser(Integer userId, UserRequestDto dto) {
        User user = findUser(userId);
        userMapper.updateEntity(dto, user);
        if (dto.getBranchId() != 0) {
            user.setBranch(resolveBranch(dto.getBranchId()));
        }
        if (dto.getRoleIds() != null) {
            user.setRoles(resolveRoles(dto.getRoleIds()));
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.delete(findUser(userId));
    }

    @Override
    public UserResponseDto assignRole(Integer userId, Integer roleId) {
        User user = findUser(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
        user.getRoles().add(role);
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto removeRole(Integer userId, Integer roleId) {
        User user = findUser(userId);
        user.getRoles().removeIf(role -> role.getId() == roleId);
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto assignToBranch(Integer userId, Integer branchId) {
        User user = findUser(userId);
        user.setBranch(resolveBranch(branchId));
        return userMapper.toResponseDto(userRepository.save(user));
    }

    // @Override
    // public UserResponseDto removeFromBranch(Integer userId, Integer branchId) {
    //     User user = findUser(userId);
    //     if (user.getBranch() != null && user.getBranch().getId() == branchId) {
    //         user.setBranch(null);
    //     }
    //     return userMapper.toResponseDto(userRepository.save(user));
    // }


    private User findUser(Integer userId) {
    return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private Branch resolveBranch(int branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));
    }

    private Set<Role> resolveRoles(Set<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(roleRepository.findAllById(roleIds));
    }
}
