package com.openclassrooms.mddapi.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user of the application.
 *
 * <p>
 * A user:
 * <ul>
 *     <li>Has a unique email address used for authentication</li>
 *     <li>Stores an encrypted password</li>
 *     <li>Has an optional username displayed in the application</li>
 * </ul>
 * </p>
 *
 * <p>
 * Users are stored in the "users" table.
 * </p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    /**
     * Unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique email address used for login.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Encrypted password of the user.
     * Stored using BCrypt hashing.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Display name of the user.
     */
    private String username;
}