package com.dbterm.dbtermback.domain.accountant.application;

import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.AllocationStatus;
import com.dbterm.dbtermback.domain.accountant.entity.Receiver;
import com.dbterm.dbtermback.domain.accountant.repository.AllocationRepository;
import com.dbterm.dbtermback.domain.accountant.repository.ReceiverRepository;
import com.dbterm.dbtermback.domain.donor.entity.Donation;
import com.dbterm.dbtermback.domain.donor.repository.DonationRepository;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AllocationAutoService {

    private static final String DEFAULT_RECEIVER_NAME = "배분 미정";
    private static final String DEFAULT_RECEIVER_TYPE = "SYSTEM";
    private static final String DEFAULT_RECEIVER_BANK_ACCOUNT = "UNASSIGNED";

    private final AllocationRepository allocationRepository;
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final ReceiverRepository receiverRepository;

    public AllocationAutoService(AllocationRepository allocationRepository,
                                 DonationRepository donationRepository,
                                 CampaignRepository campaignRepository,
                                 ReceiverRepository receiverRepository) {
        this.allocationRepository = allocationRepository;
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.receiverRepository = receiverRepository;
    }

    @Transactional
    public void createAutoAllocationForCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        boolean alreadyExists = allocationRepository.existsByCampaignId(campaignId);
        if (alreadyExists) {
            return;
        }

        List<Donation> donations = donationRepository.findByCampaignIdAndVerifiedTrue(campaignId);
        if (donations.isEmpty()) {
            return;
        }

        Receiver defaultReceiver = getOrCreateDefaultReceiver();

        for (Donation donation : donations) {
            if (donation.getAmount() == null || donation.getAmount() <= 0.0) {
                continue;
            }

            Allocation allocation = new Allocation();
            allocation.setDonationId(donation.getId());
            allocation.setCampaignId(campaignId);
            allocation.setReceiverId(defaultReceiver.getId());
            allocation.setAmount(donation.getAmount());
            allocation.setStatus(AllocationStatus.PENDING);

            allocationRepository.save(allocation);
        }
    }

    private Receiver getOrCreateDefaultReceiver() {
        Optional<Receiver> optional =
                receiverRepository.findFirstByNameAndType(DEFAULT_RECEIVER_NAME, DEFAULT_RECEIVER_TYPE);
        if (optional.isPresent()) {
            return optional.get();
        }

        Receiver receiver = new Receiver();
        receiver.setName(DEFAULT_RECEIVER_NAME);
        receiver.setType(DEFAULT_RECEIVER_TYPE);
        receiver.setBankAccount(DEFAULT_RECEIVER_BANK_ACCOUNT);

        return receiverRepository.save(receiver);
    }
}
