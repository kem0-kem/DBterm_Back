package com.dbterm.dbtermback.domain.operator.application;

import com.dbterm.dbtermback.domain.operator.dto.request.ApproveAllocationsRequest;
import com.dbterm.dbtermback.domain.operator.dto.response.AllocationApprovalResultResponse;
import com.dbterm.dbtermback.domain.operator.dto.response.AllocationApprovalResultResponse.SkippedItem;
import com.dbterm.dbtermback.domain.operator.repository.AllocationAdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApproveAllocationsService {

    private final AllocationAdminRepository allocationAdminRepository;

    public ApproveAllocationsService(AllocationAdminRepository allocationAdminRepository) {
        this.allocationAdminRepository = allocationAdminRepository;
    }

    @Transactional
    public AllocationApprovalResultResponse approve(ApproveAllocationsRequest request) {
        List<Long> ids = request.getAllocationIds();
        if (ids == null || ids.isEmpty()) {
            return new AllocationApprovalResultResponse(List.of(), List.of());
        }

        Set<Long> uniqueIds = new HashSet<>(ids);
        List<Long> pendingIds = allocationAdminRepository.findPendingAllocationIdsByIds(uniqueIds);
        Set<Long> pendingSet = new HashSet<>(pendingIds);

        int updated = allocationAdminRepository.approvePendingAllocations(pendingSet);

        List<Long> approved = new ArrayList<>(pendingSet);

        List<SkippedItem> skipped = new ArrayList<>();
        for (Long id : uniqueIds) {
            if (!pendingSet.contains(id)) {
                skipped.add(new SkippedItem(id, "NOT_PENDING_OR_NOT_FOUND"));
            }
        }

        return new AllocationApprovalResultResponse(approved, skipped);
    }
}
