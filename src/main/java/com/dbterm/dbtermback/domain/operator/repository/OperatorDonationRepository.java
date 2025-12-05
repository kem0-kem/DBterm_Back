package com.dbterm.dbtermback.domain.operator.repository;

import com.dbterm.dbtermback.domain.donor.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OperatorDonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByVerifiedFalseAndDonatedAtBetween(LocalDateTime start, LocalDateTime end);
    // 1) 캠페인별 전체 모금액 (verified 상관없이)
    @Query("SELECT COALESCE(SUM(d.amount), 0) " +
            "FROM Donation d " +
            "WHERE d.campaignId = :campaignId")
    Double sumAllAmountByCampaignId(@Param("campaignId") Long campaignId);

    // 2) (필요하면 유지) 검증된 금액 합계
    @Query("SELECT COALESCE(SUM(d.amount), 0) " +
            "FROM Donation d " +
            "WHERE d.campaignId = :campaignId " +
            "AND d.verified = true")
    Double sumVerifiedAmountByCampaignId(@Param("campaignId") Long campaignId);
}
