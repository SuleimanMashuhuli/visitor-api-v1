package com.abcbank.visitor.domain.dto;

import java.time.LocalDateTime;

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
public class PermissionResponseDto {
    private int id;
    private String name;
    private String description;
    private String resource;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
