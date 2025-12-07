package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.auth.entity.User;
import com.dbterm.dbtermback.domain.auth.repository.UserRepository;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.operator.dto.response.UnverifiedDonationResponse;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import com.dbterm.dbtermback.domain.operator.repository.OperatorDonationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class GetUnverifiedDonationsService {

    private final OperatorDonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public GetUnverifiedDonationsService(OperatorDonationRepository donationRepository,
                                         CampaignRepository campaignRepository,
                                         UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    public List<UnverifiedDonationResponse> getUnverifiedDonations(LocalDate date) {
        LocalDate target = (date == null) ? LocalDate.now() : date;
        LocalDateTime startOfDay = target.atStartOfDay();
        LocalDateTime endOfDay = target.atTime(LocalTime.MAX);

        List<Donation> donations =
                donationRepository.findByVerifiedFalseAndDonatedAtBetween(startOfDay, endOfDay);

        return donations.stream()
                .map(donation -> {
                    Campaign campaign = campaignRepository.findById(donation.getCampaignId())
                            .orElse(null);
                    String campaignTitle = campaign != null ? campaign.getTitle() : null;

                    User donor = userRepository.findById(donation.getDonorId())
                            .orElse(null);
                    String donorName = donor != null ? donor.getName() : null;

                    return new UnverifiedDonationResponse(
                            donation.getId(),
                            donation.getDonorId(),
                            donorName,
                            donation.getCampaignId(),
                            campaignTitle,
                            donation.getAmount(),
                            donation.getPaymentMethod(),
                            donation.getDonatedAt(),
                            donation.getVerified() != null && donation.getVerified()
                    );
                })
                .toList();
    }
}
