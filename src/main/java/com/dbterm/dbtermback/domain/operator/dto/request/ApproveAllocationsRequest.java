package com.dbterm.dbtermback.domain.operator.dto.request;

import java.util.List;

public class ApproveAllocationsRequest {

    private List<Long> allocationIds;

    public ApproveAllocationsRequest() {
    }

    public ApproveAllocationsRequest(List<Long> allocationIds) {
        this.allocationIds = allocationIds;
    }

    public List<Long> getAllocationIds() {
        return allocationIds;
    }

    public void setAllocationIds(List<Long> allocationIds) {
        this.allocationIds = allocationIds;
    }
}
