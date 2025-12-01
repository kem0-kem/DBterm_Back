package com.dbterm.dbtermback.domain.auth.application;

import com.dbterm.dbtermback.domain.auth.dto.request.SignUpRequest;
import com.dbterm.dbtermback.domain.auth.entity.User;
import com.dbterm.dbtermback.domain.auth.entity.UserRole;
import com.dbterm.dbtermback.domain.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignUpService {

    private final UserRepository userRepository;

    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setPhoneNum(request.getPhoneNum());
        user.setEmail("default@dbterm.local");
        user.setRole(UserRole.DONOR);

        userRepository.save(user);
    }
}
