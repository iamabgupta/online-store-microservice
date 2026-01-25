package com.abhishek.catalog.dto;

/**
 * Data Transfer Object (DTO) representing a login request.
 *
 * <p>This object carries user credentials submitted during authentication.
 * It is used only for login and is never persisted.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>username – unique user identifier</li>
 *   <li>password – raw password provided by client</li>
 * </ul>
 *
 * <p>This DTO separates external API input from internal security logic.</p>
 */
public class LoginRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
