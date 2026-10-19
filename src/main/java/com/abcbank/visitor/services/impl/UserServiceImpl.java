package com.abcbank.visitor.services.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.dto.UserRequestDto;
import com.abcbank.visitor.domain.dto.UserResponseDto;
import com.abcbank.visitor.domain.dto.Auth.ChangePasswordRequestDto;
import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.mappers.UserMapper;
import com.abcbank.visitor.repositories.UserRepository;
import com.abcbank.visitor.services.AppNotification;
import com.abcbank.visitor.services.UserService;
import com.abcbank.visitor.utils.Operation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AppNotification appNotification;
    private final Operation operation;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        String tempPassword = operation.generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setPasswordChangeRequired(true);
        user.setBranch(parseBranch(dto.getBranch()));
        User savedUser = userRepository.save(user);
        appNotification.sendTemporaryPassword(savedUser, tempPassword);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUser(Integer userId) {
        return userMapper.toResponseDto(findUser(userId));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto updateUser(Integer userId, UserRequestDto dto) {
        User user = findUser(userId);
        userMapper.updateEntity(dto, user);
        if (dto.getBranch() != null) {
            user.setBranch(parseBranch(dto.getBranch()));
        }
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.delete(findUser(userId));
    }

    private User findUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + userId)
                );
    }

    private BranchName parseBranch(String branch) {
        try {
            return BranchName.valueOf(branch.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid branch: " + branch);
        }
    }

    @Override
    public void changePassword(String email, ChangePasswordRequestDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found: " + email)
                );

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                user.getPassword())) {

            throw new BadCredentialsException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setPasswordChangeRequired(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            String temporaryPassword = operation.generateTemporaryPassword();
            user.setPassword(passwordEncoder.encode(temporaryPassword));
            user.setPasswordChangeRequired(true);
            userRepository.save(user);
            appNotification.sendResetPassword(user, temporaryPassword);
        });
    }
}