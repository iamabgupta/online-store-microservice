package com.abhishek.catalog.config;

import com.abhishek.catalog.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig configures Spring Security for this application.
 *
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
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Spring creates JwtAuthFilter
     * Injects it into SecurityConfig
     * Security chain gets the filter
     * @param jwtAuthFilter
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    //@Bean Used inside @Configuration class to manually define beans.
   /** @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable()).authorizeRequests(
                auth -> auth.requestMatchers(
                        "/health",
                        "/swagger-ui/**",
                        "v3/api-docs/**"
                ).permitAll().requestMatchers(HttpMethod.GET, "/products/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**")
                        .hasRole("ADMIN").anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults());

        return http.build();
    }**/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**",
                                "/swagger-ui/**",
                                "v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
               // .httpBasic(Customizer.withDefaults())  //This line make basic authentication able
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable())   //  IMPORTANT Disabling basic authenticiation
                .formLogin(form -> form.disable());            //  IMPORTANT Disabling form login

        return http.build();


    }


    //@Bean Used inside @Configuration class to manually define beans.
    @Bean
    public UserDetailsService userDetailsService(){
        var user = User.withUsername("user").password("{noop}user123").roles("USER").build();
        var admin = User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build();
        var readOnly = User.withUsername("readOnly").password("{noop}readOnly123").roles("READ_ONLY").build();

        return new InMemoryUserDetailsManager(user, admin, readOnly);
    }


    /**
     * Exposes the AuthenticationManager bean used for processing authentication requests.
     *
     * <p>The AuthenticationManager delegates authentication to the configured
     * UserDetailsService and password validation mechanisms.</p>
     *
     * <p>This bean is required for programmatic authentication, such as
     * login endpoints that authenticate credentials manually.</p>
     */

    //@Bean Used inside @Configuration class to manually define beans.F
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
