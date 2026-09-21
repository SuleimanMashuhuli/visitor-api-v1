package com.abcbank.visitor.services;

public interface EmailService {
    void sendTemporaryPassword(
        String to,
        String username,
        String tempPassword
    );
}
