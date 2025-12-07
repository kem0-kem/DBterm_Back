package com.dbterm.dbtermback.domain.operator.api;

import com.dbterm.dbtermback.domain.operator.application.ApproveAllocationsService;
import com.dbterm.dbtermback.domain.operator.dto.request.ApproveAllocationsRequest;
import com.dbterm.dbtermback.domain.operator.dto.response.AllocationApprovalResultResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/allocations")
public class AllocationApprovalController {

    private final ApproveAllocationsService approveAllocationsService;

    public AllocationApprovalController(ApproveAllocationsService approveAllocationsService) {
        this.approveAllocationsService = approveAllocationsService;
    }

    @PreAuthorize("hasRole('OPERATOR') or hasRole('ADMIN')")
    @PostMapping("/approve")
    public AllocationApprovalResultResponse approveAllocations(@RequestBody ApproveAllocationsRequest request) {
        return approveAllocationsService.approve(request);
    }
}
