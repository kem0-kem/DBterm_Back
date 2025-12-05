package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import org.springframework.stereotype.Service;

@Service
public class CampaignFundingService {

    private final DonationRepository donationRepository;

    public CampaignFundingService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Double getCollectedAmount(Long campaignId) {
        Double result = donationRepository.sumAmountByCampaignId(campaignId);
        return result != null ? result : 0.0;
    }

    public Double getVerifiedCollectedAmount(Long campaignId) {
        Double result = donationRepository.sumVerifiedAmountByCampaignId(campaignId);
        return result != null ? result : 0.0;
    }
}
