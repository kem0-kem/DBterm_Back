package com.dbterm.dbtermback.domain.auth.dto.response;

import com.dbterm.dbtermback.domain.auth.entity.UserRole;

public class LoginResponse {

    private final String token;
    private final UserRole role;
    private final Long userId;
    private final String username;
    private final String name;

    public LoginResponse(String token, UserRole role, Long userId, String username, String name) {
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.username = username;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public UserRole getRole() {
        return role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
