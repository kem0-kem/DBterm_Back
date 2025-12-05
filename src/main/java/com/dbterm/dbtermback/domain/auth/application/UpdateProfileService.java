package com.dbterm.dbtermback.domain.auth.application;

import com.dbterm.dbtermback.domain.auth.dto.request.UpdateProfileRequest;
import com.dbterm.dbtermback.domain.auth.entity.User;
import com.dbterm.dbtermback.domain.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProfileService {

    private final UserRepository userRepository;

    public UpdateProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getIsAnonymous() != null) {
            user.setAnonymous(request.getIsAnonymous());
        }

        // 더티체킹으로 자동 update
    }
}
