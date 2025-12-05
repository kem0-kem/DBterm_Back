package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.operator.dto.response.CampaignListItemResponse;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class GetCampaignListService {

    private final CampaignRepository campaignRepository;

    public GetCampaignListService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public Page<CampaignListItemResponse> getCampaigns(String status, String sortBy, Pageable pageable) {
        List<Campaign> all = campaignRepository.findAll();
        LocalDate today = LocalDate.now();

        String normalizedStatus = status == null ? "active" : status.toLowerCase(Locale.ROOT);
        List<Campaign> filtered = all.stream()
                .filter(campaign -> filterByStatus(campaign, normalizedStatus, today))
                .collect(Collectors.toList());

        String normalizedSort = sortBy == null ? "name" : sortBy.toLowerCase(Locale.ROOT);
        Comparator<Campaign> comparator = buildComparator(normalizedSort);
        filtered.sort(comparator);

        int total = filtered.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int fromIndex = currentPage * pageSize;
        if (fromIndex >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }
        int toIndex = Math.min(fromIndex + pageSize, total);

        List<CampaignListItemResponse> content = filtered.subList(fromIndex, toIndex)
                .stream()
                .map(CampaignListItemResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, total);
    }

    private boolean filterByStatus(Campaign campaign, String status, LocalDate today) {
        LocalDate start = campaign.getStartDate();
        LocalDate end = campaign.getEndDate();

        if ("upcoming".equals(status)) {
            return start != null && start.isAfter(today);
        }
        if ("ended".equals(status)) {
            return end != null && end.isBefore(today);
        }
        if ("all".equals(status)) {
            return true;
        }
        boolean startsBeforeOrToday = start == null || !start.isAfter(today);
        boolean endsAfterOrToday = end == null || !end.isBefore(today);
        return startsBeforeOrToday && endsAfterOrToday;
    }

    private Comparator<Campaign> buildComparator(String sortBy) {
        if ("startdate".equals(sortBy)) {
            return Comparator.comparing(Campaign::getStartDate, Comparator.nullsLast(Comparator.naturalOrder()));
        }
        if ("enddate".equals(sortBy)) {
            return Comparator.comparing(Campaign::getEndDate, Comparator.nullsLast(Comparator.naturalOrder()));
        }
        if ("createdat".equals(sortBy)) {
            return Comparator.comparing(Campaign::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()));
        }
        return Comparator.comparing(Campaign::getTitle, Comparator.nullsLast(String::compareToIgnoreCase));
    }
}
