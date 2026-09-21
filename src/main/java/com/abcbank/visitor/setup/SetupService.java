package com.abcbank.visitor.setup;

import com.abcbank.visitor.config.InitialAdminProperties;
import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.domain.enums.RoleName;
import com.abcbank.visitor.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InitialAdminProperties adminProperties;

    @Transactional
    public void initialize() {

        if (userRepository.count() > 0) {
            log.info("System already initialized. Skipping initial setup.");
            return;
        }

        log.info("No users found. Starting initial system setup...");

        BranchName branchName = BranchName.valueOf(
                adminProperties.branch()
                        .trim()
                        .toUpperCase(Locale.ROOT)
        );

        User admin = User.builder()
                .username(adminProperties.username())
                .email(adminProperties.email())
                .password(passwordEncoder.encode(adminProperties.password()))
                .roleName(RoleName.ADMIN)
                .active(true)
                .passwordChangeRequired(false)
                .branch(branchName)
                .build();

        userRepository.save(admin);

        log.info(
                "Initial administrator '{}' created successfully.",
                adminProperties.username()
        );
    }
}