package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.operator.dto.response.UnverifiedDonationResponse;
import com.dbterm.dbtermback.domain.operator.repository.OperatorDonationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUnverifiedDonationsService {

    private final OperatorDonationRepository donationRepository;

    public GetUnverifiedDonationsService(OperatorDonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<UnverifiedDonationResponse> getUnverifiedDonations(LocalDate date) {
        LocalDate target = date == null ? LocalDate.now() : date;
        LocalDateTime startOfDay = target.atStartOfDay();
        LocalDateTime endOfDay = target.atTime(LocalTime.MAX);

        List<Donation> donations = donationRepository.findByVerifiedFalseAndDonatedAtBetween(startOfDay, endOfDay);

        return donations.stream()
                .map(UnverifiedDonationResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
