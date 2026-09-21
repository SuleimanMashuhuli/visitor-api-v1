package com.abcbank.visitor.domain.entities;

import com.abcbank.visitor.domain.enums.BranchName;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "branches")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Branch {

    @Id
    @Enumerated(EnumType.STRING)
    private BranchName branch;
    @Builder.Default
    private boolean active = true;
}