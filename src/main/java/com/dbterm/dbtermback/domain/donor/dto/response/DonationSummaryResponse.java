package com.dbterm.dbtermback.domain.donor.dto.response;

import java.time.LocalDateTime;

public class DonationSummaryResponse {

    private Long donationId;
    private Long campaignId;
    private Double amount;
    private String paymentMethod;
    private LocalDateTime donatedAt;
    private Boolean verified;

    public DonationSummaryResponse(Long donationId, Long campaignId, Double amount, String paymentMethod, LocalDateTime donatedAt, Boolean verified) {
        this.donationId = donationId;
        this.campaignId = campaignId;
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
