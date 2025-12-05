package com.dbterm.dbtermback.domain.donor.repository;

import com.dbterm.dbtermback.domain.donor.entity.Donation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonorId(Long donorId, Sort sort);

    List<Donation> findByDonorIdAndDonatedAtBetween(Long donorId,
                                                    LocalDateTime start,
                                                    LocalDateTime end,
                                                    Sort sort);

    List<Donation> findByCampaignIdAndVerifiedTrue(Long campaignId);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.campaignId = :campaignId")
    Double sumAmountByCampaignId(@Param("campaignId") Long campaignId);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.campaignId = :campaignId AND d.verified = true")
    Double sumVerifiedAmountByCampaignId(@Param("campaignId") Long campaignId);

    @Query(
            value = "SELECT " +
                    "d.donation_id AS donationId, " +
                    "d.amount AS donationAmount, " +
                    "d.verified AS donationVerified, " +
                    "d.donated_at AS donationDonatedAt, " +
                    "c.campaign_id AS campaignId, " +
                    "c.title AS campaignTitle, " +
                    "a.allocation_id AS allocationId, " +
                    "a.amount AS allocationAmount, " +
                    "a.status AS allocationStatus, " +
                    "r.receiver_id AS receiverId, " +
                    "r.name AS receiverName, " +
                    "r.type AS receiverType, " +
                    "ds.disbursement_id AS disbursementId, " +
                    "ds.amount AS disbursementAmount, " +
                    "ds.status AS disbursementStatus, " +
                    "ds.payment_tx_ref AS paymentTxRef, " +
                    "u.user_id AS donorId, " +
                    "u.username AS donorUsername, " +
                    "u.is_anonymous AS donorIsAnonymous " +
                    "FROM donation d " +
                    "JOIN campaign c ON c.campaign_id = d.campaign_id " +
                    "JOIN app_user u ON u.user_id = d.donor_id " +
                    "LEFT JOIN allocation a ON a.donation_id = d.donation_id " +
                    "LEFT JOIN receiver r ON r.receiver_id = a.receiver_id " +
                    "LEFT JOIN disbursement ds ON ds.allocation_id = a.allocation_id " +
                    "WHERE d.campaign_id = :campaignId " +
                    "ORDER BY d.donation_id, a.allocation_id",
            nativeQuery = true
    )
    List<DonationTraceRow> findCampaignTrace(@Param("campaignId") Long campaignId);
}
