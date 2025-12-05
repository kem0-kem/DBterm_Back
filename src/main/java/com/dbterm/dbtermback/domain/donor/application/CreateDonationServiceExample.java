package com.dbterm.dbtermback.domain.donor.application;

import com.dbterm.dbtermback.domain.auditor.application.AuditLoggingService;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 참고용 예시입니다.
 * 실제 프로젝트의 CreateDonationService와 시그니처/내용이 다를 수 있으니
 * 아래 auditLoggingService.recordEntityChange(...) 부분만 가져다 쓰세요.
 */
@Service
@Transactional
public class CreateDonationServiceExample {

    private final DonationRepository donationRepository;
    private final AuditLoggingService auditLoggingService;

    public CreateDonationServiceExample(DonationRepository donationRepository, AuditLoggingService auditLoggingService) {
        this.donationRepository = donationRepository;
        this.auditLoggingService = auditLoggingService;
    }

    public Donation createDonation(Donation donation) {
        Donation saved = donationRepository.save(donation);

        auditLoggingService.recordEntityChange(
                "DONATION_CREATED",
                "donation",
                saved.getId(),
                null,
                saved
        );

        return saved;
    }
}
