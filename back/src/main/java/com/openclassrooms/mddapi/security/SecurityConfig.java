package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Central Spring Security configuration class.
 *
 * <p>
 * This configuration:
 * <ul>
 *     <li>Disables CSRF protection (stateless REST API)</li>
 *     <li>Configures JWT-based authentication</li>
 *     <li>Defines public and secured endpoints</li>
 *     <li>Sets session management to STATELESS</li>
 *     <li>Configures CORS policy</li>
 *     <li>Provides a password encoder bean</li>
 * </ul>
 * </p>
 *
 * <p>
 * The application uses JWT authentication instead of session-based security.
 * </p>
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    /**
     * Constructs the security configuration.
     *
     * @param jwtFilter custom JWT authentication filter
     */
    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Configures the security filter chain.
     *
     * <p>
     * Security rules:
     * <ul>
     *     <li>Endpoints under /auth/** are publicly accessible</li>
     *     <li>All other endpoints require authentication</li>
     *     <li>JWT filter is executed before the default authentication filter</li>
     * </ul>
     * </p>
     *
     * <p>
     * Session policy is set to STATELESS to ensure the API does not
     * rely on HTTP sessions.
     * </p>
     *
     * @param http the {@link HttpSecurity} configuration object
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS).
     *
     * <p>
     * Currently allows requests from:
     * <ul>
     *     <li>http://localhost:4200</li>
     * </ul>
     * </p>
     *
     * <p>
     * In production, allowed origins should be restricted appropriately.
     * </p>
     *
     * @return the configured {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Provides a password encoder bean.
     *
     * <p>
     * Uses BCrypt hashing algorithm for secure password storage.
     * </p>
     *
     * @return a {@link PasswordEncoder} implementation
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}