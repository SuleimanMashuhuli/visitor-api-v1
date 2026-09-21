package com.abcbank.visitor.services.impl;

import com.abcbank.visitor.domain.dto.MailMessage;
import com.abcbank.visitor.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final RestTemplate restTemplate;
    @Value("${app.constants.email-api.url}")
    private String emailUrl;

    @Override
    public void sendTemporaryPassword(String email, String username, String temporaryPassword) {

        String subject = "Account Created";

        String body = """
                Hello %s,

                Your account has been created successfully.

                Username: %s
                Temporary Password: %s

                Please log in using the temporary password and change your
                password immediately.

                Regards,
                System Administrator
                """.formatted(
                username,
                username,
                temporaryPassword
        );

        MailMessage message = new MailMessage(email, subject, body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MailMessage> request = new HttpEntity<>(message, headers);

        String endpoint = emailUrl + "/api/v1/api/email-sender";

        log.info("Sending account creation email to '{}'", email);

        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class );

        log.info("Email API response status: {}", response.getStatusCode() );

        log.info("Temporary password email sent to '{}'", email);
    }
}