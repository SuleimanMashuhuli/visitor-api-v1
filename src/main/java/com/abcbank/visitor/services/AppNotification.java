package com.abcbank.visitor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.domain.entities.Visit;
import com.abcbank.visitor.utils.Emailer;
import com.abcbank.visitor.utils.Operation;

import lombok.RequiredArgsConstructor;

@Service    
@RequiredArgsConstructor 
public class AppNotification {
    private final Emailer emailer;

    @Value("${app.config.param.email-from}")
    private String from;

    @Value("${app.config.param.url}")
    private String backendUrl;

    public void sendTemporaryPassword(User user, String temporaryPassword) {
        String name = user.getFirstName();
        String subject = "Account Created";
        String body = String.format(
            "Hello %s, your account has been created successfully.\n"
            + "\n\n Temporary Password: %s"
            + "\n\n  Please log in using the temporary password and change your password immediately."
            + "\n\n\n Regards"
            + "\n System Administrator"
            , name, temporaryPassword
        );
        String to = user.getEmail();
        emailer.send(from, to, subject, body);
    }

    public void sendResetPassword(User user, String temporaryPassword) {
        String name = user.getFirstName() + user.getLastName();
        String subject = "Password Reset";
        String body = String.format(
            "Hello %s, your Password has been reset successfully.\n"
            + "\n\nPasscode: %s"
            + "\n\nPlease reset your password by using the nPasscode and change your password immediately."
            + "\n\n\nRegards"
            + "\nSystem Administrator"
            , name, temporaryPassword
        );
        String to = user.getEmail();
        emailer.send(from, to, subject, body);
    }

    public void sendVisitNotification(Visit visit) {
        String actions = Operation.visitActions(visit.getId(), backendUrl);
        String name = visit.getHostName();
        String subject = "New Visitor";
        String body = String.format(
            "Hello %s, a new visit has been scheduled and requires your attention.\n"
            + "\n\nVisitor: %s %s"
            + "\nPurpose: %s"
            + "\nScheduled At: %s"
            + "\n\n%s\n\n"
            + "\n\n\nRegards"
            + "\nSystem Administrator"
            , name, visit.getVisitor().getFirstName(), visit.getVisitor().getLastName(), visit.getScheduledAt(), visit.getPurpose(), actions
        );
        String to = visit.getHostEmail();
        emailer.send(from, to, subject, body);
    }
}