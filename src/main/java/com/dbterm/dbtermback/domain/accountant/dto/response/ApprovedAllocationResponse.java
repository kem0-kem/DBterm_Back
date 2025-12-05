package com.dbterm.dbtermback.domain.accountant.dto.response;

import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.AllocationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ApprovedAllocationResponse {

    private Long allocationId;
    private Long donationId;
    private Long campaignId;
    private Long receiverId;
    private BigDecimal amount;
    private AllocationStatus status;
    private OffsetDateTime createdAt;

    public ApprovedAllocationResponse(Long allocationId,
                                      Long donationId,
                                      Long campaignId,
                                      Long receiverId,
                                      BigDecimal amount,
                                      AllocationStatus status,
                                      OffsetDateTime createdAt) {
        this.allocationId = allocationId;
        this.donationId = donationId;
        this.campaignId = campaignId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static ApprovedAllocationResponse fromEntity(Allocation allocation) {
        BigDecimal amount = allocation.getAmount() != null
                ? BigDecimal.valueOf(allocation.getAmount())
                : null;

        LocalDateTime createdAtLocal = allocation.getCreatedAt();
        OffsetDateTime createdAtOffset = createdAtLocal != null
                ? createdAtLocal.atOffset(ZoneOffset.UTC)   // 필요하면 원하는 타임존으로 변경
                : null;

        return new ApprovedAllocationResponse(
                allocation.getId(),
                allocation.getDonationId(),
                allocation.getCampaignId(),
                allocation.getReceiverId(),
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

    public Long getReceiverId() {
        return receiverId;
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
