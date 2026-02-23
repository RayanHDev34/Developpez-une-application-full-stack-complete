package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authentication and user registration logic.
 *
 * <p>
 * This service handles:
 * <ul>
 *     <li>User login with credential validation</li>
 *     <li>User registration with password hashing</li>
 *     <li>JWT token generation</li>
 * </ul>
 * </p>
 *
 * <p>
 * It ensures secure password verification using {@link PasswordEncoder}
 * and generates authentication tokens via {@link JwtService}.
 * </p>
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an {@link AuthService} with required dependencies.
     *
     * @param userRepository  repository used for user persistence
     * @param userMapper      mapper used to convert {@link User} to DTO
     * @param passwordEncoder component used for secure password hashing and verification
     * @param jwtService      service responsible for JWT token generation
     */
    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user based on email and password.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Retrieves the user by email</li>
     *     <li>Validates the provided password</li>
     *     <li>Generates a JWT token if authentication succeeds</li>
     *     <li>Returns an {@link AuthResponse} containing the token and user information</li>
     * </ol>
     * </p>
     *
     * @param request the login request containing email and password
     * @return an {@link AuthResponse} containing the JWT token and user data
     * @throws RuntimeException if the credentials are invalid
     */
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId());

        return new AuthResponse(
                token,
                userMapper.toDto(user)
        );
    }

    /**
     * Registers a new user account.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Checks if the email is already registered</li>
     *     <li>Hashes the password using {@link PasswordEncoder}</li>
     *     <li>Persists the new user</li>
     *     <li>Generates a JWT token for immediate authentication</li>
     * </ol>
     * </p>
     *
     * @param request the registration request containing user information
     * @return an {@link AuthResponse} containing the JWT token and user data
     * @throws RuntimeException if the email is already registered
     */
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getId());

        return new AuthResponse(
                token,
                userMapper.toDto(user)
        );
    }
}