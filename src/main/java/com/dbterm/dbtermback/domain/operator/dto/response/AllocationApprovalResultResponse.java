package com.dbterm.dbtermback.domain.operator.dto.response;

import java.util.List;

public class AllocationApprovalResultResponse {

    public static class SkippedItem {
        private Long allocationId;
        private String reason;

        public SkippedItem() {
        }

        public SkippedItem(Long allocationId, String reason) {
            this.allocationId = allocationId;
            this.reason = reason;
        }

        public Long getAllocationId() {
            return allocationId;
        }

        public String getReason() {
            return reason;
        }
    }

    private List<Long> approved;
    private List<SkippedItem> skipped;

    public AllocationApprovalResultResponse(List<Long> approved, List<SkippedItem> skipped) {
        this.approved = approved;
        this.skipped = skipped;
    }

    public List<Long> getApproved() {
        return approved;
    }

    public List<SkippedItem> getSkipped() {
        return skipped;
    }
}
