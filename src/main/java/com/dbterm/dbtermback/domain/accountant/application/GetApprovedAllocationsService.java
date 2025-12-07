package com.dbterm.dbtermback.domain.accountant.application;

import com.dbterm.dbtermback.domain.accountant.dto.response.ApprovedAllocationResponse;
import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.AllocationStatus;
import com.dbterm.dbtermback.domain.accountant.entity.Receiver;
import com.dbterm.dbtermback.domain.accountant.repository.AllocationRepository;
import com.dbterm.dbtermback.domain.accountant.repository.ReceiverRepository;
import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import com.dbterm.dbtermback.domain.operator.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetApprovedAllocationsService {

    private final AllocationRepository allocationRepository;
    private final CampaignRepository campaignRepository;
    private final ReceiverRepository receiverRepository;

    public GetApprovedAllocationsService(AllocationRepository allocationRepository,
                                         CampaignRepository campaignRepository,
                                         ReceiverRepository receiverRepository) {
        this.allocationRepository = allocationRepository;
        this.campaignRepository = campaignRepository;
        this.receiverRepository = receiverRepository;
    }

    @Transactional(readOnly = true)
    public List<ApprovedAllocationResponse> getApprovedAllocations() {
        List<Allocation> allocations =
                allocationRepository.findByStatus(AllocationStatus.APPROVED);

        return allocations.stream()
                .map(allocation -> {
                    Campaign campaign = campaignRepository.findById(allocation.getCampaignId())
                            .orElse(null);
                    String campaignTitle = campaign != null ? campaign.getTitle() : null;

                    Receiver receiver = receiverRepository.findById(allocation.getReceiverId())
                            .orElse(null);
                    String receiverName = receiver != null ? receiver.getName() : null;

                    return ApprovedAllocationResponse.from(allocation, campaignTitle, receiverName);
                })
                .collect(Collectors.toList());
    }
}
