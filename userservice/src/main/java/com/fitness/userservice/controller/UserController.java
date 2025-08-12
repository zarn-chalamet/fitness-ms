package com.fitness.userservice.controller;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable("userId") String userId) {
        UserResponse userResponse = userService.getUserProfileByUserId(userId);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse userResponse = userService.registerNewUser(request);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{keycloakId}/validate")
    public ResponseEntity<Boolean> validateUserByUserId(@PathVariable("keycloakId") String keycloakId) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("this ran :" +keycloakId);
        return ResponseEntity.ok(userService.validateUser(keycloakId));
    }

}
