package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for managing user-related operations.
 *
 * <p>
 * This controller exposes endpoints to:
 * <ul>
 *     <li>Retrieve the authenticated user's profile</li>
 *     <li>Update the authenticated user's information</li>
 * </ul>
 * </p>
 *
 * <p>
 * All endpoints are mapped under the base path <b>/users</b>.
 * </p>
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a {@link UserController} with the required {@link UserService}.
     *
     * @param userService the service responsible for user business logic
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * and used to fetch the corresponding user data.
     * </p>
     *
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing the {@link UserResponse}
     *         representing the user's profile information
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        UserResponse userResponse =
                userService.getUserById(user.getId());

        return ResponseEntity.ok(userResponse);
    }

    /**
     * Updates the profile information of the currently authenticated user.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * and used to apply the requested updates.
     * </p>
     *
     * @param request        the request containing updated user information
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing the updated {@link UserResponse}
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
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