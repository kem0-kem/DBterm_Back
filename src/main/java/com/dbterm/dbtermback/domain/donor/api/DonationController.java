package com.dbterm.dbtermback.domain.donor.api;

import com.dbterm.dbtermback.domain.donor.application.CreateDonationService;
import com.dbterm.dbtermback.domain.donor.dto.request.CreateDonationRequest;
import com.dbterm.dbtermback.domain.donor.dto.response.CreateDonationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final CreateDonationService createDonationService;

    public DonationController(CreateDonationService createDonationService) {
        this.createDonationService = createDonationService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createDonation(@RequestBody CreateDonationRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long donorId = (Long) auth.getPrincipal();

        // 서비스 내부에서 목표액 초과 검증 + Donation 저장이 동시에 처리됨
        Long response = createDonationService.createDonation(donorId, request);

        return ResponseEntity.ok(response);
    }
}
