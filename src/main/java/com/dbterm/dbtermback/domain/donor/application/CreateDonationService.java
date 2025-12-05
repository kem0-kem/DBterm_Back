package com.dbterm.dbtermback.domain.donor.application;

import com.dbterm.dbtermback.domain.donor.dto.request.CreateDonationRequest;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import com.dbterm.dbtermback.domain.operator.application.CampaignFundingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDonationService {

    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignFundingService campaignFundingService;

    public CreateDonationService(DonationRepository donationRepository,
                                 CampaignRepository campaignRepository,
                                 CampaignFundingService campaignFundingService) {
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.campaignFundingService = campaignFundingService;
    }

    @Transactional
    public Long createDonation(Long donorId, CreateDonationRequest request) {

        Campaign campaign = campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Campaign not found: " + request.getCampaignId())
                );

        Double goal = campaign.getGoalAmount();
        Long requestedAmount = request.getAmount();

        if (goal == null || requestedAmount == null || requestedAmount <= 0L) {
            Donation donation = Donation.create(
                    donorId,
                    request.getCampaignId(),
                    requestedAmount != null ? requestedAmount.doubleValue() : 0.0,
                    request.getPaymentMethod()
            );
            Donation saved = donationRepository.save(donation);
            return saved.getId();
        }

        Double collectedBefore = campaignFundingService.getCollectedAmount(campaign.getId());

        if (collectedBefore >= goal) {
            throw new IllegalStateException("This campaign has already reached its goal amount.");
        }

        double remaining = goal - collectedBefore;
        long cappedAmount = requestedAmount;
        if (requestedAmount.doubleValue() > remaining) {
            cappedAmount = (long) Math.floor(remaining);
        }

        if (cappedAmount <= 0L) {
            throw new IllegalStateException("This campaign has already reached its goal amount.");
        }

        Donation donation = new Donation();
        donation.setDonorId(donorId);
        donation.setCampaignId(request.getCampaignId());
        donation.setAmount((double) cappedAmount);
        donation.setPaymentMethod(request.getPaymentMethod());

        Donation saved = donationRepository.save(donation);
        return saved.getId();
    }
}
