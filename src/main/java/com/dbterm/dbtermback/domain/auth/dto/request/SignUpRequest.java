package com.dbterm.dbtermback.domain.auth.dto.request;

public class SignUpRequest {

    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }
}
