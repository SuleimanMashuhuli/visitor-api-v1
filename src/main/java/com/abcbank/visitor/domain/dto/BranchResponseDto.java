package com.abcbank.visitor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDto {
    private int id;
    private String name;
    private long users;
    private boolean active;
}