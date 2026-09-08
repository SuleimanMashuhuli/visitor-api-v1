package com.abcbank.visitor.services.impl;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.abcbank.visitor.domain.dto.NotificationResponseDto;
import com.abcbank.visitor.domain.entities.Notification;
import com.abcbank.visitor.domain.entities.Visit;
import com.abcbank.visitor.domain.enums.NotificationStatus;
import com.abcbank.visitor.mappers.SystemMapper;
import com.abcbank.visitor.repositories.NotificationRepository;
import com.abcbank.visitor.repositories.VisitRepository;
import com.abcbank.visitor.services.NotificationService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final VisitRepository visitRepository;
    private final SystemMapper systemMapper;
    private final JavaMailSender mailSender;

    @Override
    public NotificationResponseDto trackNotification(Integer notificationId) {
        return systemMapper.toResponseDto(findNotification(notificationId));
    }

    @Override
    public NotificationResponseDto createNotification(Integer visitId) {
        return buildNotification(visitId, "ARRIVAL_ALERT",
                "Visitor check-in",
                "Your visitor has checked in at the branch.");
    }

    private NotificationResponseDto buildNotification(Integer visitId, String type, String subject, String message) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found with id: " + visitId));

        Notification notification = Notification.builder()
                .recipientEmail(visit.getHostEmail())
                .subject(subject)
                .message(message)
                .notificationType(type)
                .status(NotificationStatus.PENDING)
                .visit(visit)
                .build();

        return systemMapper.toResponseDto(notificationRepository.save(notification));
    }

    @Override
    public void sendNotification(Integer notificationId) {
        Notification notification = findNotification(notificationId);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getRecipientEmail());
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());
            mailSender.send(message);

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(e.getMessage());
        }
        notificationRepository.save(notification);
    }

    private Notification findNotification(Integer notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + notificationId));
    }
}
