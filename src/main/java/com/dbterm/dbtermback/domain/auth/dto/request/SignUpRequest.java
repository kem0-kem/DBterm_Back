package com.dbterm.dbtermback.domain.auth.dto.request;

import com.dbterm.dbtermback.domain.auth.entity.UserRole;

public class SignUpRequest {

    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private UserRole role;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getPhoneNum() { return phoneNum; }
    public UserRole getRole() { return role; }
}
