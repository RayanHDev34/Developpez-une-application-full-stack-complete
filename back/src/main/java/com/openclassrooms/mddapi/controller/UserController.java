package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        UserResponse userResponse = userService.getUserById(user.getId());
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        UserResponse updatedUserResponse =
                userService.updateUser(user.getId(), request);

        return ResponseEntity.ok(updatedUserResponse);
    }
}