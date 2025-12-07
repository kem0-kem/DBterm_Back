package com.dbterm.dbtermback.domain.donor.dto.response;

import java.time.LocalDateTime;

public class DonationSummaryResponse {

    private final Long donationId;
    private final Long campaignId;
    private final String campaignTitle;
    private final Double amount;
    private final String paymentMethod;
    private final LocalDateTime donatedAt;
    private final Boolean verified;

    public DonationSummaryResponse(Long donationId,
                                   Long campaignId,
                                   String campaignTitle,
                                   Double amount,
                                   String paymentMethod,
                                   LocalDateTime donatedAt,
                                   Boolean verified) {
        this.donationId = donationId;
        this.campaignId = campaignId;
        this.campaignTitle = campaignTitle;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.donatedAt = donatedAt;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getDonatedAt() {
        return donatedAt;
    }

    public Boolean getVerified() {
        return verified;
    }
}
