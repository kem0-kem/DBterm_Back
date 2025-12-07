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

    /**
     * 캠페인 목록 조회
     * - status 필터 없이 모든 캠페인 조회
     * - 기본 정렬 기준: id 오름차순
     */
    @GetMapping
    public Page<CampaignListItemResponse> getCampaigns(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return getCampaignListService.getCampaigns(sortBy, pageable);
    }
}
