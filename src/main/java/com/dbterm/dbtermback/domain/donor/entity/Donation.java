package com.dbterm.dbtermback.domain.donor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long id;

    @Column(name = "donor_id", nullable = false)
    private Long donorId;

    @Column(name = "campaign_id", nullable = false)
    private Long campaignId;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;

    @Column(name = "donated_at", insertable = false, updatable = false)
    private LocalDateTime donatedAt;

    @Column(nullable = false)
    private Boolean verified = false;

    public Donation() {
    }

    public static Donation create(Long donorId, Long campaignId, Double amount, String paymentMethod) {
        Donation d = new Donation();
        d.donorId = donorId;
        d.campaignId = campaignId;
        d.amount = amount;
        d.paymentMethod = paymentMethod;
        d.verified = false;
        return d;
    }

    public Long getId() {
        return id;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
