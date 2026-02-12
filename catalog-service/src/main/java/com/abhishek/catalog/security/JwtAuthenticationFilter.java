package com.abhishek.catalog.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This is the heart of JWT security.
 * Why OncePerRequestFilter?
 * Guarantees: Filter runs once per request
 * Prevents duplicate execution
 * Perfect for JWT.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //This method is called by Spring for every HTTP request.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        System.out.println("=== JWT FILTER ENTERED ===");
        System.out.println("URI = " + request.getRequestURI());
        System.out.println("AUTH HEADER = " + request.getHeader("Authorization"));


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                var authentication = JwtUtil.buildAuthentication(token);
                //THIS IS THE MOST IMPORTANT LINE It tells Spring Security: “This request is authenticated.”
                //From this moment:
                //@PreAuthorize works
                //hasRole() works
                //Controller sees authenticated user
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(authentication.getName());
                System.out.println(authentication.getAuthorities());
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
