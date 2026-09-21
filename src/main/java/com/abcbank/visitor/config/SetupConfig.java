package com.abcbank.visitor.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InitialAdminProperties.class)
public class SetupConfig {
}