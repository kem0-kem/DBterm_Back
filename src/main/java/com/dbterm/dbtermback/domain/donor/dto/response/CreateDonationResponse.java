package com.dbterm.dbtermback.domain.donor.dto.response;

import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;

public class CreateDonationResponse {
    private Long donationId;

    public CreateDonationResponse(Long donationId) {
        this.donationId = donationId;
    }

    public Long getDonationId() {
        return donationId;
    }
}
