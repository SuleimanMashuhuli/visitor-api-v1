package com.abcbank.visitor.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialSetup implements ApplicationRunner {

    private final SetupService setupService;

    @Override
    public void run(ApplicationArguments args) {
        setupService.initialize();
    }
}