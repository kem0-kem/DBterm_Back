package com.dbterm.dbtermback.domain.operator.dto.response;

import java.time.LocalDateTime;

public class UnverifiedDonationResponse {

    private final Long donationId;
    private final Long donorId;
    private final String donorName;
    private final Long campaignId;
    private final String campaignTitle;
    private final Double amount;
    private final String paymentMethod;
    private final LocalDateTime donatedAt;
    private final boolean verified;

    public UnverifiedDonationResponse(Long donationId,
                                      Long donorId,
                                      String donorName,
                                      Long campaignId,
                                      String campaignTitle,
                                      Double amount,
                                      String paymentMethod,
                                      LocalDateTime donatedAt,
                                      boolean verified) {
        this.donationId = donationId;
        this.donorId = donorId;
        this.donorName = donorName;
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

    public Long getDonorId() {
        return donorId;
    }

    public String getDonorName() {
        return donorName;
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

    public boolean isVerified() {
        return verified;
    }
}
