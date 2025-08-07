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
    public UserResponse getUserProfileByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        return UserMapper.userToUserDto(user);
    }

    @Override
    public UserResponse registerNewUser(RegisterRequest request) {

        //check user is already register or not
        User isEmailAlreadyRegistered = userRepository.findByEmail(request.getEmail());
        if(isEmailAlreadyRegistered != null){
            throw new RuntimeException("Email already registered");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        User savedUser = userRepository.save(newUser);

        return UserMapper.userToUserDto(savedUser);
    }
}
