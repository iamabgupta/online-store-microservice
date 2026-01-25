package com.abhishek.catalog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class responsible for generating JSON Web Tokens (JWT).
 *
 * <p>This class creates signed JWT tokens after successful authentication.
 * The generated token contains:</p>
 *
 * <ul>
 *   <li>Username (as the subject)</li>
 *   <li>User roles (as a custom claim)</li>
 *   <li>Issued time</li>
 *   <li>Expiration time</li>
 * </ul>
 *
 * <p>The token is signed using a secret key and the HS256 algorithm.
 * This ensures token integrity and prevents tampering.</p>
 *
 * <p>This class is intentionally stateless and does not store any session
 * or user data.</p>
 *
 */
public class JwtUtil {


    private static final SecretKey SECRET_KEY =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_MS = 60 * 60 * 1000; // 1 hour

    public static String generateToken(UserDetails userDetails) {

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }
}
