package com.abcbank.visitor.services;

import java.util.List;

import com.abcbank.visitor.domain.dto.UserRequestDto;
import com.abcbank.visitor.domain.dto.UserResponseDto;
import com.abcbank.visitor.domain.dto.Auth.ChangePasswordRequestDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUser(Integer userId);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);
    void deleteUser(Integer userId);
    void changePassword(String email, ChangePasswordRequestDto dto);
    void resetPassword(String email);
}
