package com.abcbank.visitor.services;

import com.abcbank.visitor.domain.dto.NotificationResponseDto;

public interface NotificationService {
    NotificationResponseDto trackNotification(Integer notitcationId);
    NotificationResponseDto createNotification(Integer visitId);
    void sendNotification(Integer notificationId);
}
