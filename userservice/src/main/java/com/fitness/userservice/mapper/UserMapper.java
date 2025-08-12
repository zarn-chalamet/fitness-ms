package com.fitness.userservice.mapper;

import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;

public class UserMapper {

    public static UserResponse userToUserDto(User user) {

        if (user == null) return null;

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        //encode password here
        userResponse.setKeycloakId(user.getKeycloakId());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setRole(user.getRole());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }
}
