package com.abhishek.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig configures Spring Security for this application.
 *
 * <p>This configuration enables basic security controls for REST APIs and
 * prepares the application for JWT-based authentication.</p>
 *
 * <h2>Key Responsibilities</h2>
 * <ul>
 *   <li>Disables CSRF protection because this application exposes stateless REST APIs.</li>
 *   <li>Allows unauthenticated access to health check and API documentation endpoints.</li>
 *   <li>Secures all business APIs by requiring authentication.</li>
 *   <li>Uses HTTP Basic authentication temporarily for initial security setup.</li>
 * </ul>
 *
 * <h2>Why CSRF is Disabled</h2>
 * <p>
 * CSRF protection is designed for session-based, browser-driven applications.
 * Since this application uses REST APIs and will rely on token-based authentication
 * (JWT), CSRF protection is unnecessary and therefore disabled.
 * </p>
 *
 * <h2>Open Endpoints</h2>
 * <ul>
 *   <li><b>/health</b> – Used by monitoring systems for health checks.</li>
 *   <li><b>/swagger-ui/**</b> – Provides interactive API documentation.</li>
 *   <li><b>/v3/api-docs/**</b> – Exposes OpenAPI specification used by Swagger UI.</li>
 * </ul>
 *
 * <h2>Secured Endpoints</h2>
 * <p>
 * All other endpoints require authentication. Requests without valid credentials
 * will receive a <b>401 Unauthorized</b> response.
 * </p>
 *
 * <h2>Future Enhancements</h2>
 * <ul>
 *   <li>Replace HTTP Basic authentication with JWT-based authentication.</li>
 *   <li>Introduce role-based authorization (USER / ADMIN).</li>
 *   <li>Secure APIs with stateless token validation.</li>
 * </ul>
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable()).authorizeRequests(
                auth -> auth.requestMatchers(
                        "/health",
                        "/swagger-ui/**",
                        "v3/api-docs/**"
                ).permitAll().anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
