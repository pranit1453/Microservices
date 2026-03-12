package com.demo.auth.controller;

import com.demo.auth.dto.request.SignupRequest;
import com.demo.auth.dto.response.AuthResponse;
import com.demo.auth.generic.ApiResponse;
import com.demo.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public ResponseEntity<ApiResponse<AuthResponse>> signUpNewUser(@Valid @RequestBody SignupRequest request){
        AuthResponse response = authService.createNewUser(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder().status(HttpStatus.CREATED.value()).success(true).message("User created successfully").data(response).timestamp(Instant.now()).build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
