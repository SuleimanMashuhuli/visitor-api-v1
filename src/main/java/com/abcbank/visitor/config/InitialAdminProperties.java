package com.abcbank.visitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.setup.admin")
public record InitialAdminProperties(String username, String email, String password, String branch) {
}