package com.abcbank.visitor.services.impl;

import java.security.SecureRandom;
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
import com.abcbank.visitor.services.EmailService;
import com.abcbank.visitor.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);

        String tempPassword = generateTemporaryPassword();

        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setPasswordChangeRequired(true);
        user.setBranch(parseBranch(dto.getBranch()));

        User savedUser = userRepository.save(user);

        emailService.sendTemporaryPassword(
                savedUser.getEmail(),
                savedUser.getUsername(),
                tempPassword
        );

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
                        new EntityNotFoundException(
                                "User not found with id: " + userId
                        )
                );
    }

    private BranchName parseBranch(String branch) {
        try {
            return BranchName.valueOf(branch.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid branch: " + branch
            );
        }
    }

    private String generateTemporaryPassword() {
        String characters =
                "ABCDEFGHJKLMNPQRSTUVWXYZ" +
                "abcdefghijkmnopqrstuvwxyz" +
                "123456789" +
                "@#$%";

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            password.append(
                    characters.charAt(
                            random.nextInt(characters.length())
                    )
            );
        }

        return password.toString();
    }

    @Override
    public void changePassword(
            String username,
            ChangePasswordRequestDto dto) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found: " + username
                        )
                );

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Current password is incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );

        user.setPasswordChangeRequired(false);

        userRepository.save(user);
    }
}