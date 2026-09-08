package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

import com.abcbank.visitor.domain.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private int id;
    private String recipientEmail;
    private String notificationType;
    private String subject;
    private String message;
    private int visitId;
    private NotificationStatus status;
    private String failureReason;
    private LocalDateTime sentAt;
    private LocalDateTime updatedAt;
}
