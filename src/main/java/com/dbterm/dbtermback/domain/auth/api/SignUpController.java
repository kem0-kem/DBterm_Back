package com.dbterm.dbtermback.domain.auth.api;

import com.dbterm.dbtermback.domain.auth.application.SignUpService;
import com.dbterm.dbtermback.domain.auth.dto.request.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        signUpService.signUp(request);
        return new ResponseEntity<>("signup success", HttpStatus.CREATED);
    }
}
