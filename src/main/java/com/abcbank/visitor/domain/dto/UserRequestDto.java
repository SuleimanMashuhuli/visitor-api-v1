package com.abcbank.visitor.domain.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
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
public class UserRequestDto {
    private String employeeId;
    @NotBlank private String username;
    private String firstName;
    private String lastName;
    @NotBlank private String email;
    private String phoneNumber;
    @NotBlank private String password;
    private Set<Integer> roleIds;
    private int branchId;
}
