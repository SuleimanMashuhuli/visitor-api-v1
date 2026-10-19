package com.abcbank.visitor.utils;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class Emailer {
    @Value("${app.config.param.smtphost}")
    private String smtpHost;

    public boolean send(String from, String to, String subject, String body) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpHost);
        Session session = Session.getDefaultInstance(properties, null);

        try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body, "utf-8", "html");
                Transport.send(message);
                System.out.print("message sent successfully...");
                return true;
        } catch (MessagingException e) {
                e.printStackTrace();
                return false;
        }
    }
}