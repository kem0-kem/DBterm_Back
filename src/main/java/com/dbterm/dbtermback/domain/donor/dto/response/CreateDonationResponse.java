package com.dbterm.dbtermback.domain.donor.dto.response;

public class CreateDonationResponse {

    private final Long donationId;
    private final Long campaignId;
    private final String campaignTitle;
    private final Double amount;
    private final boolean verified;

    public CreateDonationResponse(Long donationId,
                                  Long campaignId,
                                  String campaignTitle,
                                  Double amount,
                                  boolean verified) {
        this.donationId = donationId;
        this.campaignId = campaignId;
        this.campaignTitle = campaignTitle;
        this.amount = amount;
        this.verified = verified;
    }

    public Long getDonationId() {
        return donationId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public String getCampaignTitle() {
        return campaignTitle;
    }

    public Double getAmount() {
        return amount;
    }

    public boolean isVerified() {
        return verified;
    }
}
