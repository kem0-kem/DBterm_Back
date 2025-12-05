package com.dbterm.dbtermback.domain.donor.api;

import com.dbterm.dbtermback.domain.donor.application.GetCampaignTraceService;
import com.dbterm.dbtermback.domain.donor.dto.response.CampaignTraceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donations")
public class CampaignTraceController {

    private final GetCampaignTraceService getCampaignTraceService;

    public CampaignTraceController(GetCampaignTraceService getCampaignTraceService) {
        this.getCampaignTraceService = getCampaignTraceService;
    }

    @GetMapping("/{campaignId}/trace")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CampaignTraceResponse> getCampaignTrace(@PathVariable Long campaignId) {
        CampaignTraceResponse response = getCampaignTraceService.getCampaignTrace(campaignId);
        return ResponseEntity.ok(response);
    }
}
