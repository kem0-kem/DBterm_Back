package com.dbterm.dbtermback.domain.auth.dto.request;

public class SignUpRequest {

    private String username;
    private String password;
    private String name;
    private String phoneNum;

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
}
