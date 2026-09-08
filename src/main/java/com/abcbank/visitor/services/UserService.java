package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.UserRequestDto;
import com.abcbank.visitor.domain.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUser(Integer userId);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);
    void deleteUser(Integer userId);

    UserResponseDto assignRole(Integer userId, Integer roleId);
    UserResponseDto removeRole(Integer userId, Integer roleId);

    UserResponseDto assignToBranch(Integer userId, Integer branchId);
    // UserResponseDto removeFromBranch(Integer userId, Integer branchId);
}
