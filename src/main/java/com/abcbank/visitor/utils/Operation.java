package com.abcbank.visitor.utils;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class Operation {

    private static final String CHARACTERS =
            "ABCDEFGHJKLMNPQRSTUVWXYZ" +
            "abcdefghijkmnopqrstuvwxyz" +
            "123456789" +
            "@#$%";

    private final SecureRandom random = new SecureRandom();

    public String generateTemporaryPassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    public static String visitActions(Integer visitId, String baseUrl) {
    String acceptUrl = baseUrl + "/api/v1/visits/" + visitId + "/accept";
    String rejectUrl = baseUrl + "/api/v1/visits/" + visitId + "/reject";

    return """
            <p>Please choose an action:</p>
            <a href="%s"
                style="display:inline-block;padding:12px 24px;
                background-color:#198754;color:#ffffff;
                text-decoration:none;border-radius:5px;
                font-weight:bold;">
                Accept Visit
            </a>
            &nbsp;
            <a href="%s"
                style="display:inline-block;padding:12px 24px;
                background-color:#dc3545;color:#ffffff;
                text-decoration:none;border-radius:5px;
                font-weight:bold;">
                Reject Visit
            </a>
            """.formatted(acceptUrl, rejectUrl);
    }
}
