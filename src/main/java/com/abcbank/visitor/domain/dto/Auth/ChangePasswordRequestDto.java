package com.abcbank.visitor.domain.dto.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
}
