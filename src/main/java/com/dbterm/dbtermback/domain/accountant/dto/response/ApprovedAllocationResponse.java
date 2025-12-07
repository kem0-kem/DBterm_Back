package com.dbterm.dbtermback.domain.accountant.dto.response;

import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.AllocationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ApprovedAllocationResponse {

    private final Long allocationId;
    private final Long donationId;
    private final Long campaignId;
    private final String campaignTitle;
    private final Long receiverId;
    private final String receiverName;
    private final BigDecimal amount;
    private final AllocationStatus status;
    private final OffsetDateTime createdAt;

    public ApprovedAllocationResponse(Long allocationId,
                                      Long donationId,
                                      Long campaignId,
                                      String campaignTitle,
                                      Long receiverId,
                                      String receiverName,
                                      BigDecimal amount,
                                      AllocationStatus status,
                                      OffsetDateTime createdAt) {
        this.allocationId = allocationId;
        this.donationId = donationId;
        this.campaignId = campaignId;
        this.campaignTitle = campaignTitle;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static ApprovedAllocationResponse from(Allocation allocation,
                                                  String campaignTitle,
                                                  String receiverName) {
        BigDecimal amount = allocation.getAmount() != null
                ? BigDecimal.valueOf(allocation.getAmount())
                : null;

        LocalDateTime createdAtLocal = allocation.getCreatedAt();
        OffsetDateTime createdAtOffset = createdAtLocal != null
                ? createdAtLocal.atOffset(ZoneOffset.UTC)
                : null;

        return new ApprovedAllocationResponse(
                allocation.getId(),
                allocation.getDonationId(),
                allocation.getCampaignId(),
                campaignTitle,
                allocation.getReceiverId(),
                receiverName,
                amount,
                allocation.getStatus(),
                createdAtOffset
        );
    }

    public Long getAllocationId() {
        return allocationId;
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

    public Long getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AllocationStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
