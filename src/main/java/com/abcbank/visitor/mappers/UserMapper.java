package com.abcbank.visitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.abcbank.visitor.domain.dto.UserRequestDto;
import com.abcbank.visitor.domain.dto.UserResponseDto;
import com.abcbank.visitor.domain.entities.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntity(UserRequestDto userRequestDto, @MappingTarget User user);

    UserResponseDto toResponseDto(User user);
}
