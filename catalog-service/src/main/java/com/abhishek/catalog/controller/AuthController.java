package com.abhishek.catalog.controller;

import com.abhishek.catalog.dto.LoginRequest;
import com.abhishek.catalog.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller responsible for authentication operations.
 *
 * <p>This controller exposes a login endpoint that authenticates users
 * and returns a JWT upon successful authentication.</p>
 *
 * <p>Authentication flow:</p>
 * <ol>
 *   <li>Client submits username and password</li>
 *   <li>Spring Security authenticates credentials</li>
 *   <li>JWT is generated with user roles</li>
 *   <li>Token is returned to the client</li>
 * </ol>
 *
 * <p>This controller does not manage sessions or cookies.
 * Authentication is stateless and token-based.</p>
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )

        );

        String token = JwtUtil.generateToken(
                (org.springframework.security.core.userdetails.UserDetails)
                        authentication.getPrincipal()
        );

        return ResponseEntity.ok(
                Map.of("token", token)



        );
    }
}
