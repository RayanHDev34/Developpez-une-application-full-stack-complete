package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.payload.response.UserResponse;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing user-related business logic.
 *
 * <p>
 * This service handles:
 * <ul>
 *     <li>Retrieving user profile information</li>
 *     <li>Updating user account data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Password updates are securely handled using {@link PasswordEncoder}.
 * </p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@link UserService} with required dependencies.
     *
     * @param userRepository repository used for user persistence
     * @param passwordEncoder component used for secure password hashing
     */
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves a user by its unique identifier.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Fetches the user from the database</li>
     *     <li>Maps the entity to a {@link UserResponse}</li>
     * </ol>
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@link UserResponse} representing the user profile
     * @throws RuntimeException if the user does not exist
     */
    public UserResponse getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toResponse(user);
    }

    /**
     * Updates the information of an existing user.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Validates the existence of the user</li>
     *     <li>Updates only non-null and non-blank fields</li>
     *     <li>Encodes the password if it is provided</li>
     *     <li>Persists the updated user</li>
     * </ol>
     * </p>
     *
     * <p>
     * Fields that are null or blank are ignored and remain unchanged.
     * </p>
     *
     * @param userId  the identifier of the user to update
     * @param request the request containing updated user information
     * @return a {@link UserResponse} representing the updated user
     * @throws RuntimeException if the user does not exist
     */
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }
}