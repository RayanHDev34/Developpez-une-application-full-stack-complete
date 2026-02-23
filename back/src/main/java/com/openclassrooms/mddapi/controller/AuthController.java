package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for authentication operations.
 *
 * <p>
 * This controller exposes endpoints for:
 * <ul>
 *     <li>User authentication (login)</li>
 *     <li>User registration (account creation)</li>
 * </ul>
 * </p>
 *
 * <p>
 * All endpoints are mapped under the base path <b>/auth</b>.
 * </p>
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructs an {@link AuthController} with the required {@link AuthService}.
     *
     * @param authService the service responsible for authentication business logic
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user using the provided credentials.
     *
     * <p>
     * If the credentials are valid, an authentication token and related
     * user information are returned in the response.
     * </p>
     *
     * @param request the login request containing authentication credentials
     * @return a {@link ResponseEntity} containing the {@link AuthResponse}
     *         with authentication details (e.g., JWT token)
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a new user account.
     *
     * <p>
     * If the registration is successful, an authentication response
     * is returned, typically containing a token for immediate login.
     * </p>
     *
     * @param request the registration request containing user information
     *                such as email, username, and password
     * @return a {@link ResponseEntity} containing the {@link AuthResponse}
     *         with authentication details for the newly created user
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);

        return ResponseEntity.ok(response);
    }
}