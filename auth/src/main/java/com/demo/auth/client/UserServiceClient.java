package com.demo.auth.client;

import com.demo.auth.dto.request.CreateUserProfileRequest;
import com.demo.auth.dto.response.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE",url = "${services.USER-SERVICE.url}")
public interface UserServiceClient {

    @PostMapping("/api/users")
    UserDetailsResponse createUser(@RequestBody CreateUserProfileRequest request );
}
