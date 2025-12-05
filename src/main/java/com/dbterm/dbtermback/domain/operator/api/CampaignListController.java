package com.dbterm.dbtermback.domain.operator.api;

import com.dbterm.dbtermback.domain.operator.application.GetCampaignListService;
import com.dbterm.dbtermback.domain.operator.dto.response.CampaignListItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignListController {

    private final GetCampaignListService getCampaignListService;

    public CampaignListController(GetCampaignListService getCampaignListService) {
        this.getCampaignListService = getCampaignListService;
    }

    @GetMapping
    public Page<CampaignListItemResponse> getCampaigns(
            @RequestParam(name = "status", required = false, defaultValue = "active") String status,
            @RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return getCampaignListService.getCampaigns(status, sortBy, pageable);
    }
}
