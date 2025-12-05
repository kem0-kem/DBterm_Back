package com.dbterm.dbtermback.domain.operator.dto.response;

import com.dbterm.dbtermback.domain.donor.entity.Donation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UnverifiedDonationResponse {

    private Long donationId;
    private Long donorId;
    private Long campaignId;
    private Double amount;
    private String paymentMethod;
    private LocalDateTime donatedAt;
    private boolean verified;

    public UnverifiedDonationResponse(Long donationId, Long donorId, Long campaignId,
                                      Double amount, String paymentMethod,
                                      LocalDateTime donatedAt, boolean verified) {
        this.donationId = donationId;
        this.donorId = donorId;
        this.campaignId = campaignId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.donatedAt = donatedAt;
        this.verified = verified;
    }

    public static UnverifiedDonationResponse fromEntity(Donation donation) {
        return new UnverifiedDonationResponse(
                donation.getId(),
                donation.getDonorId(),
                donation.getCampaignId(),
                donation.getAmount(),
                donation.getPaymentMethod(),
                donation.getDonatedAt(),
                donation.getVerified()
        );
    }

    public Long getDonationId() {
        return donationId;
    }

    public Long getDonorId() {
        return donorId;
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

    public boolean isVerified() {
        return verified;
    }
}
