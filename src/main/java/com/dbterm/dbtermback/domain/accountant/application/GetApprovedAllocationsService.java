package com.dbterm.dbtermback.domain.accountant.application;

import com.dbterm.dbtermback.domain.accountant.dto.response.ApprovedAllocationResponse;
import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.AllocationStatus;
import com.dbterm.dbtermback.domain.accountant.repository.AllocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetApprovedAllocationsService {

    private final AllocationRepository allocationRepository;

    public GetApprovedAllocationsService(AllocationRepository allocationRepository) {
        this.allocationRepository = allocationRepository;
    }

    @Transactional(readOnly = true)
    public List<ApprovedAllocationResponse> getApprovedAllocations() {
        List<Allocation> allocations = allocationRepository.findByStatus(AllocationStatus.APPROVED);
        return allocations.stream()
                .map(ApprovedAllocationResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
