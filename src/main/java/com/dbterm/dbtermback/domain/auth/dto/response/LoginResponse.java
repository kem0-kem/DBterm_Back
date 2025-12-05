package com.dbterm.dbtermback.domain.auth.dto.response;

import com.dbterm.dbtermback.domain.auth.entity.UserRole;

public class LoginResponse {
    private String token;
    private UserRole role;
    private Long userId;

    public LoginResponse(String token, UserRole role, Long userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
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
}
