package com.dbterm.dbtermback.domain.auth.application;

import com.dbterm.dbtermback.domain.auth.dto.request.LoginRequest;
import com.dbterm.dbtermback.domain.auth.dto.response.LoginResponse;
import com.dbterm.dbtermback.domain.auth.entity.User;
import com.dbterm.dbtermback.domain.auth.repository.UserRepository;
import com.dbterm.dbtermback.global.jwt.TokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = tokenProvider.generateToken(user.getId(), user.getRole());

        return new LoginResponse(token, user.getRole(), user.getId());
    }
}
