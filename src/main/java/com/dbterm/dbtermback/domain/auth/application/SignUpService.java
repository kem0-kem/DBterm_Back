package com.dbterm.dbtermback.domain.auth.application;

import com.dbterm.dbtermback.domain.auth.dto.request.SignUpRequest;
import com.dbterm.dbtermback.domain.auth.entity.User;
import com.dbterm.dbtermback.domain.auth.entity.UserRole;
import com.dbterm.dbtermback.domain.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setName(request.getName());
        user.setPhoneNum(request.getPhoneNum());
        user.setEmail(request.getEmail() != null ? request.getEmail() : "default@dbterm.local");//더미,추후구현예정
        user.setRole(UserRole.DONOR);

        userRepository.save(user);
    }
}
