package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Service responsible for JSON Web Token (JWT) generation and validation.
 *
 * <p>
 * This service provides methods to:
 * <ul>
 *     <li>Generate JWT tokens for authenticated users</li>
 *     <li>Extract user identifiers from tokens</li>
 *     <li>Validate token integrity and expiration</li>
 * </ul>
 * </p>
 *
 * <p>
 * Tokens are signed using the HMAC-SHA256 algorithm (HS256).
 * </p>
 *
 * <p>
 * ⚠️ In a production environment, the secret key must be stored securely
 * in configuration properties or environment variables.
 * </p>
 */
@Service
public class JwtService {

    /**
     * Secret key used to sign JWT tokens.
     * <p>
     * ⚠️ Should be externalized in application configuration in production.
     * </p>
     */
    private static final String SECRET =
            "thisIsASecretKeyForJwtThatMustBeVeryLongAndSecure123456";

    /**
     * Token expiration time in milliseconds (24 hours).
     */
    private static final long EXPIRATION = 1000 * 60 * 60 * 24;

    /**
     * Generates the signing key used for token signature verification.
     *
     * @return the cryptographic {@link Key} used for signing JWT tokens
     */
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Generates a JWT token for a given user.
     *
     * <p>
     * The generated token:
     * <ul>
     *     <li>Uses the user ID as the subject</li>
     *     <li>Contains the issue date</li>
     *     <li>Contains an expiration date</li>
     *     <li>Is signed using HS256</li>
     * </ul>
     * </p>
     *
     * @param userId the unique identifier of the authenticated user
     * @return a signed JWT token as a {@link String}
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the user identifier from a JWT token.
     *
     * <p>
     * The token is parsed and validated before extracting the subject.
     * </p>
     *
     * @param token the JWT token
     * @return the user identifier contained in the token
     * @throws JwtException if the token is invalid or cannot be parsed
     * @throws IllegalArgumentException if the token is null or malformed
     */
    public Long extractUserId(String token) {

        String subject = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return Long.parseLong(subject);
    }

    /**
     * Validates the integrity and expiration of a JWT token.
     *
     * <p>
     * The token is considered valid if:
     * <ul>
     *     <li>It is correctly signed</li>
     *     <li>It has not expired</li>
     *     <li>It is properly formatted</li>
     * </ul>
     * </p>
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}