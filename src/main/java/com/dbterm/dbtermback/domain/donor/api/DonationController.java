package com.dbterm.dbtermback.domain.donor.api;

import com.dbterm.dbtermback.domain.donor.application.CreateDonationService;
import com.dbterm.dbtermback.domain.donor.dto.request.CreateDonationRequest;
import com.dbterm.dbtermback.domain.donor.dto.response.CreateDonationResponse;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final CreateDonationService createDonationService;
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;

    public DonationController(CreateDonationService createDonationService,
                              DonationRepository donationRepository,
                              CampaignRepository campaignRepository) {
        this.createDonationService = createDonationService;
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CreateDonationResponse> createDonation(@RequestBody CreateDonationRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long donorId = (Long) auth.getPrincipal();

        Long donationId = createDonationService.createDonation(donorId, request);

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("Donation not found: " + donationId));

        Campaign campaign = campaignRepository.findById(donation.getCampaignId())
                .orElse(null);

        String campaignTitle = campaign != null ? campaign.getTitle() : null;

        CreateDonationResponse response = new CreateDonationResponse(
                donation.getId(),
                donation.getCampaignId(),
                campaignTitle,
                donation.getAmount(),
                Boolean.TRUE.equals(donation.getVerified())
        );

        return ResponseEntity.ok(response);
    }
}
