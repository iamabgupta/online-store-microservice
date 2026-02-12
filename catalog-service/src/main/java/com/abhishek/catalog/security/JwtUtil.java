package com.abhishek.catalog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    /**
     * @param token
     * @return
     *
     * Create JWT parser
     * Set signing key (for validation)
     * Parse token
     * Verify signature
     * Verify expiration
     * Return payload (Claims)
     * If token invalid → exception thrown
     * If valid → claims returned
     */
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Spring Security does not understand JWT directly.
     * It understands: Authentication object.
     * So we convert JWT → Authentication.
     *
     *
     * @param token
     * @return
     */
    public static UsernamePasswordAuthenticationToken buildAuthentication(String token) {
        Claims claims = extractClaims(token);

        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }

}
