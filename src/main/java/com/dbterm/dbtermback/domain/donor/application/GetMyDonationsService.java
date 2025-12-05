package com.dbterm.dbtermback.domain.donor.application;

import com.dbterm.dbtermback.domain.donor.dto.response.DonationSummaryResponse;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetMyDonationsService {

    private final DonationRepository donationRepository;

    public GetMyDonationsService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Transactional(readOnly = true)
    public List<DonationSummaryResponse> getMyDonations(Long donorId, String period, String sortBy, String order) {
        Sort sort = buildSort(sortBy, order);

        List<Donation> donations;

        if ("this_month".equalsIgnoreCase(period)) {
            LocalDate now = LocalDate.now();
            LocalDateTime start = now.withDayOfMonth(1).atStartOfDay();
            LocalDateTime end = now.plusMonths(1).withDayOfMonth(1).atStartOfDay();
            donations = donationRepository.findByDonorIdAndDonatedAtBetween(donorId, start, end, sort);
        } else {
            donations = donationRepository.findByDonorId(donorId, sort);
        }

        return donations.stream()
                .map(d -> new DonationSummaryResponse(
                        d.getId(),
                        d.getCampaignId(),
                        d.getAmount(),
                        d.getPaymentMethod(),
                        d.getDonatedAt(),
                        d.getVerified()
                ))
                .toList();
    }

    private Sort buildSort(String sortBy, String order) {
        String field = "donatedAt";

        if ("amount".equalsIgnoreCase(sortBy)) {
            field = "amount";
        }

        Sort.Direction direction = Sort.Direction.DESC;
        if ("asc".equalsIgnoreCase(order)) {
            direction = Sort.Direction.ASC;
        }

        return Sort.by(direction, field);
    }
}
