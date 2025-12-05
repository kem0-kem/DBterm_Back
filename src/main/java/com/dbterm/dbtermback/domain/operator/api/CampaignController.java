package com.dbterm.dbtermback.domain.operator.api;

import com.dbterm.dbtermback.domain.operator.application.CreateCampaignService;
import com.dbterm.dbtermback.domain.operator.dto.request.CreateCampaignRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('OPERATOR')")
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CreateCampaignService createCampaignService;

    public CampaignController(CreateCampaignService createCampaignService) {
        this.createCampaignService = createCampaignService;
    }

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> createCampaign(@RequestBody CreateCampaignRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long operatorId = (Long) auth.getPrincipal();

        Long campaignId = createCampaignService.createCampaign(operatorId, request);

        return ResponseEntity.ok("campaign created: " + campaignId);
    }
}
