package com.dbterm.dbtermback.domain.auth.api;

import com.dbterm.dbtermback.domain.auth.application.UpdateProfileService;
import com.dbterm.dbtermback.domain.auth.dto.request.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserProfileController {

    private final UpdateProfileService updateProfileService;

    public UserProfileController(UpdateProfileService updateProfileService) {
        this.updateProfileService = updateProfileService;
    }

    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) auth.getPrincipal();

        updateProfileService.updateProfile(userId, request);

        return ResponseEntity.ok("profile updated");
    }
}
