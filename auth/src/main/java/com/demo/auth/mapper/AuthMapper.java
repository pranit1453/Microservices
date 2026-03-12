package com.demo.auth.mapper;

import com.demo.auth.dto.request.CreateUserProfileRequest;
import com.demo.auth.dto.request.SignupRequest;
import com.demo.auth.dto.response.AuthResponse;
import com.demo.auth.model.entities.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    // SignupRequest → CreateAuthUserRequest (Auth → User Service)
    CreateUserProfileRequest toCreateUserProfileRequest(SignupRequest request);

    // SignupRequest → Auth Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true) // password encoded in service
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "providerId", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AuthUser toAuthUser(SignupRequest request);

    // Entity → AuthResponse
    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "accessTokenExpiresAt", ignore = true)
    AuthResponse toAuthResponse(AuthUser user);
}