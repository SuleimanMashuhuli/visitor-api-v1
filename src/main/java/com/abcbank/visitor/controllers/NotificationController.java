package com.abcbank.visitor.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcbank.visitor.domain.dto.NotificationResponseDto;
import com.abcbank.visitor.services.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping(path = "/api/v1/notifications")
@RequiredArgsConstructor 
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> track(@PathVariable Integer notificationId) {
        NotificationResponseDto response = notificationService.trackNotification(notificationId);
        return ResponseEntity.ok(response);
    }

    @PostMapping 
    public ResponseEntity<NotificationResponseDto> create(@RequestBody Integer visitId) {
        NotificationResponseDto response = notificationService.createNotification(visitId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Void> send(@PathVariable Integer notificationId) {
        notificationService.sendNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}