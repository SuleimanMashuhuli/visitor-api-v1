package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

import com.abcbank.visitor.domain.enums.VisitStatus;

import jakarta.validation.constraints.Positive;
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
public class VisitResponseDto {
    private int id;
    private String hostEmail;
    private String hostName;
    private String purpose;
    @Positive private Integer durationMinutes;
    private LocalDateTime scheduledAt;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;
    private VisitStatus status;
    private int visitorId;
    private int branchId;
    private String branchName;
    private int createdBy;
    private String createdByUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
