package com.fitness.userservice.service.impl;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exceptions.UserNotFoundException;
import com.fitness.userservice.mapper.UserMapper;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import com.fitness.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserProfileByUserId(String userId) {

        User user = userRepository.findByKeycloakId(userId);

        return UserMapper.userToUserDto(user);
    }

    @Override
    public UserResponse registerNewUser(RegisterRequest request) {

        //check user is already register or not
        if(userRepository.existsByEmail(request.getEmail())) {

            User existingUser = userRepository.findByEmail(request.getEmail());

            return UserMapper.userToUserDto(existingUser);
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setKeycloakId(request.getKeycloakId());
        newUser.setPassword(request.getPassword());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        User savedUser = userRepository.save(newUser);

        return UserMapper.userToUserDto(savedUser);
    }

    @Override
    public Boolean validateUser(String keycloakId) {
//        User user = userRepository.findByKeycloakId(keycloakId);
//        if(user != null) {
//            return true;
//        }
//        return false;
        return userRepository.existsByKeycloakId(keycloakId);
    }
}
