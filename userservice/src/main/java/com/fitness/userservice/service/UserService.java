package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;

public interface UserService {
    UserResponse getUserProfileByUserId(String userId);

    UserResponse registerNewUser(RegisterRequest request);

    Boolean validateUser(String keycloakId);
}
