package com.dbterm.dbtermback.domain.auth.api;

import com.dbterm.dbtermback.domain.auth.application.LoginService;
import com.dbterm.dbtermback.domain.auth.dto.request.LoginRequest;
import com.dbterm.dbtermback.domain.auth.dto.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
