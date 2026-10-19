package com.abcbank.visitor.setup;

import com.abcbank.visitor.config.InitialAdminProperties;
import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.domain.enums.BranchName;
import com.abcbank.visitor.domain.enums.RoleName;
import com.abcbank.visitor.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(InitialAdminProperties.class)
public class InitialSetup implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InitialAdminProperties adminProperties;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        BranchName branchName = BranchName.valueOf(
                adminProperties.branch().trim().toUpperCase(Locale.ROOT));

        User admin = userRepository.findByEmail(adminProperties.email())
                .orElseGet(() -> {
                    User newAdmin = User.builder()
                            .username(adminProperties.username())
                            .email(adminProperties.email())
                            .password(passwordEncoder.encode(adminProperties.password()))
                            .roleName(RoleName.SUPER_ADMIN)
                            .build();

                    newAdmin.setActive(true);
                    newAdmin.setPasswordChangeRequired(true);
                    newAdmin.setBranch(branchName);

                    return newAdmin;
                });

        admin.setRoleName(RoleName.SUPER_ADMIN);
        admin.setActive(true);
        admin.setBranch(branchName);

        userRepository.save(admin);

        log.info("Initial Super Admin setup completed.");
    }
}
