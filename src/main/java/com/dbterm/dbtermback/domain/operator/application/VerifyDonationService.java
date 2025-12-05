package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.accountant.application.AllocationAutoService;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import com.dbterm.dbtermback.domain.operator.dto.response.UnverifiedDonationResponse;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerifyDonationService {

    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignFundingService campaignFundingService;
    private final AllocationAutoService allocationAutoService;

    public VerifyDonationService(DonationRepository donationRepository,
                                 CampaignRepository campaignRepository,
                                 CampaignFundingService campaignFundingService,
                                 AllocationAutoService allocationAutoService) {
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.campaignFundingService = campaignFundingService;
        this.allocationAutoService = allocationAutoService;
    }

    @Transactional
    public UnverifiedDonationResponse verifyDonation(Long donationId) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("Donation not found: " + donationId));

        if (Boolean.TRUE.equals(donation.getVerified())) {
            return null;
        }

        Long campaignId = donation.getCampaignId();
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        Double goal = campaign.getGoalAmount();
        if (goal == null) {
            donation.setVerified(true);
            return null;
        }

        Double collectedBefore = campaignFundingService.getVerifiedCollectedAmount(campaignId);

        donation.setVerified(true);

        Double collectedAfter = collectedBefore + donation.getAmount();

        if (collectedBefore < goal && collectedAfter >= goal) {
            allocationAutoService.createAutoAllocationForCampaign(campaignId);
        }
        return null;
    }
}
