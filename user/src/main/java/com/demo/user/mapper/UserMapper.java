package com.demo.user.mapper;

import com.demo.user.dto.request.CreateUserRequest;
import com.demo.user.dto.request.UpdateUserRequest;
import com.demo.user.dto.response.UserDetailsResponse;
import com.demo.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // convert dto to entity
    User toEntity(CreateUserRequest request);

    // convert entity to dto
    UserDetailsResponse toResponse(User user);

    // Update existing entity from dto
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateUserRequest request, @MappingTarget User user);
}
