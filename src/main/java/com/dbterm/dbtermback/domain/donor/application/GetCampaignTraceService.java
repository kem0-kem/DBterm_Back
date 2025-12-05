package com.dbterm.dbtermback.domain.donor.application;

import com.dbterm.dbtermback.domain.donor.dto.response.CampaignTraceResponse;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import com.dbterm.dbtermback.domain.donor.repository.DonationTraceRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

@Service
public class GetCampaignTraceService {

    private final DonationRepository donationRepository;

    public GetCampaignTraceService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Transactional(readOnly = true)
    public CampaignTraceResponse getCampaignTrace(Long campaignId) {
        List<DonationTraceRow> rows = donationRepository.findCampaignTrace(campaignId);

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Campaign has no donations or does not exist");
        }

        DonationTraceRow first = rows.get(0);

        CampaignTraceResponse response = new CampaignTraceResponse();
        response.setCampaignId(first.getCampaignId());
        response.setCampaignTitle(first.getCampaignTitle());

        Map<Long, CampaignTraceResponse> donationMap = new LinkedHashMap<>();
        Set<Long> countedDonations = new HashSet<>();
        Set<Long> countedDisbursements = new HashSet<>();

        double totalDonationAmount = 0.0;
        double totalDisbursedAmount = 0.0;

        for (DonationTraceRow row : rows) {
            Long donationId = row.getDonationId();
            CampaignTraceResponse donation = donationMap.get(donationId);
            if (donation == null) {
                donation = new CampaignTraceResponse(
                        row.getDonationId(),
                        row.getDonationAmount(),
                        row.getDonationVerified(),
                        row.getDonationDonatedAt()
                );
                donation.setDonorId(row.getDonorId());
                donation.setDonorAnonymous(row.getDonorIsAnonymous());

                String displayName;
                if (Boolean.TRUE.equals(row.getDonorIsAnonymous())) {
                    displayName = "익명";
                } else {
                    displayName = row.getDonorUsername();
                }
                donation.setDonorName(displayName);

                CampaignTraceResponse.CampaignInfo campaignInfo = new CampaignTraceResponse.CampaignInfo(
                        row.getCampaignId(),
                        row.getCampaignTitle()
                );
                donation.setCampaign(campaignInfo);

                donationMap.put(donationId, donation);

                if (!countedDonations.contains(donationId)) {
                    if (row.getDonationAmount() != null) {
                        totalDonationAmount += row.getDonationAmount();
                    }
                    countedDonations.add(donationId);
                }
            }

            Long allocationId = row.getAllocationId();
            if (allocationId == null) {
                continue;
            }

            CampaignTraceResponse.AllocationInfo allocation = donation.getAllocations().stream()
                    .filter(a -> allocationId.equals(a.getAllocationId()))
                    .findFirst()
                    .orElse(null);

            if (allocation == null) {
                allocation = new CampaignTraceResponse.AllocationInfo(
                        allocationId,
                        row.getAllocationAmount(),
                        row.getAllocationStatus()
                );
                donation.getAllocations().add(allocation);
            }

            Long receiverId = row.getReceiverId();
            if (receiverId != null && allocation.getReceiver() == null) {
                CampaignTraceResponse.ReceiverInfo receiver = new CampaignTraceResponse.ReceiverInfo(
                        receiverId,
                        row.getReceiverName(),
                        row.getReceiverType()
                );
                allocation.setReceiver(receiver);
            }

            Long disbursementId = row.getDisbursementId();
            if (disbursementId != null && allocation.getDisbursement() == null) {
                CampaignTraceResponse.DisbursementInfo disbursement = new CampaignTraceResponse.DisbursementInfo(
                        disbursementId,
                        row.getDisbursementAmount(),
                        row.getDisbursementStatus(),
                        row.getPaymentTxRef()
                );
                allocation.setDisbursement(disbursement);

                if (!countedDisbursements.contains(disbursementId) && row.getDisbursementAmount() != null) {
                    totalDisbursedAmount += row.getDisbursementAmount();
                    countedDisbursements.add(disbursementId);
                }
            }
        }

        response.setDonations(donationMap.values().stream().toList());
        response.setTotalDonationAmount(totalDonationAmount);
        response.setTotalDisbursedAmount(totalDisbursedAmount);

        return response;
    }
}
