package com.dbterm.dbtermback.domain.operator.dto.response;

import com.dbterm.dbtermback.domain.operator.entity.Campaign;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CampaignListItemResponse {

    private Long campaignId;
    private String title;
    private String department;
    private Double goalAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public CampaignListItemResponse(Long campaignId, String title, String department, Double goalAmount,
                                    LocalDate startDate, LocalDate endDate, LocalDateTime createdAt) {
        this.campaignId = campaignId;
        this.title = title;
        this.department = department;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

    public static CampaignListItemResponse fromEntity(Campaign campaign) {
        return new CampaignListItemResponse(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getDepartment(),
                campaign.getGoalAmount(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getCreatedAt()
        );
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public Double getGoalAmount() {
        return goalAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
