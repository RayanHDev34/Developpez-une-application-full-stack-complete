package com.openclassrooms.mddapi.security;

import javax.servlet.*;
import javax.servlet.http.*;

import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT authentication filter executed once per request.
 *
 * <p>
 * This filter:
 * <ul>
 *     <li>Intercepts incoming HTTP requests</li>
 *     <li>Extracts the JWT token from the Authorization header</li>
 *     <li>Validates the token</li>
 *     <li>Loads the associated user</li>
 *     <li>Sets the authentication in the Spring Security context</li>
 * </ul>
 * </p>
 *
 * <p>
 * Expected header format:
 * <pre>
 * Authorization: Bearer &lt;jwt-token&gt;
 * </pre>
 * </p>
 *
 * <p>
 * If the token is invalid or absent, the filter allows the request
 * to continue without authentication.
 * </p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Constructs a {@link JwtAuthenticationFilter}.
     *
     * @param jwtService     service used to validate and parse JWT tokens
     * @param userRepository repository used to retrieve users from the database
     */
    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * Performs JWT authentication logic for each incoming HTTP request.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Extracts the Authorization header</li>
     *     <li>Checks for a Bearer token</li>
     *     <li>Validates the JWT token</li>
     *     <li>Extracts the user ID from the token</li>
     *     <li>Loads the user from the database</li>
     *     <li>Creates a {@link UsernamePasswordAuthenticationToken}</li>
     *     <li>Stores it in the {@link SecurityContextHolder}</li>
     * </ol>
     * </p>
     *
     * <p>
     * If the token is missing, invalid, or the user does not exist,
     * the request proceeds without authentication.
     * </p>
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.extractUserId(token);

        userRepository.findById(userId).ifPresent(user -> {

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        });

        filterChain.doFilter(request, response);
    }
}