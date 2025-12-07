package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.operator.dto.response.CampaignListItemResponse;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetCampaignListService {

    private final CampaignRepository campaignRepository;

    public GetCampaignListService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    /**
     * 캠페인 전체 조회 + 정렬 + 페이징
     *
     * @param sortBy  정렬 기준 (id, startDate, endDate, createdAt, name)
     * @param pageable 페이징 정보
     */
    public Page<CampaignListItemResponse> getCampaigns(String sortBy, Pageable pageable) {

        // 1) 전체 캠페인 조회 (status 필터 없음)
        List<Campaign> all = campaignRepository.findAll();

        // 2) 정렬 기준 적용
        Comparator<Campaign> comparator = buildComparator(sortBy);
        List<Campaign> sorted = all.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        // 3) 페이징 슬라이스
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        if (start > end) {
            start = end;
        }

        List<CampaignListItemResponse> content = sorted.subList(start, end).stream()
                .map(CampaignListItemResponse::fromEntity)
                .toList();

        return new PageImpl<>(content, pageable, sorted.size());
    }

    private Comparator<Campaign> buildComparator(String sortBy) {
        if (sortBy == null) {
            return Comparator.comparing(Campaign::getId);
        }

        String key = sortBy.toLowerCase(Locale.ROOT);

        return switch (key) {
            case "id" -> Comparator.comparing(Campaign::getId);
            case "startdate" -> Comparator.comparing(Campaign::getStartDate);
            case "enddate" -> Comparator.comparing(Campaign::getEndDate);
            case "createdat" -> Comparator.comparing(Campaign::getCreatedAt);
            case "name", "title" -> Comparator.comparing(
                    Campaign::getTitle,
                    String.CASE_INSENSITIVE_ORDER
            );
            default -> Comparator.comparing(Campaign::getId);
        };
    }
}
