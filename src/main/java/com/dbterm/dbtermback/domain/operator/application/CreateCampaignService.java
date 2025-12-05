package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.operator.dto.request.CreateCampaignRequest;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@PreAuthorize("hasRole('OPERATOR')")
@Service
public class CreateCampaignService {

    private final CampaignRepository campaignRepository;

    public CreateCampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Transactional
    public Long createCampaign(Long operatorId, CreateCampaignRequest request) {

        Campaign campaign = Campaign.create(
                request.getName(),
                request.getDescription(),
                request.getDepartment(),
                request.getGoalAmount(),
                request.getStartDate(),
                request.getEndDate(),
                operatorId
        );

        campaignRepository.save(campaign);

        return campaign.getId();
    }
}
