package com.dbterm.dbtermback.domain.accountant.api;

import com.dbterm.dbtermback.domain.accountant.application.GetApprovedAllocationsService;
import com.dbterm.dbtermback.domain.accountant.dto.response.ApprovedAllocationResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
@PreAuthorize("hasRole('ACCOUNTANT')")
public class AllocationAccountantController {

    private final GetApprovedAllocationsService getApprovedAllocationsService;

    public AllocationAccountantController(GetApprovedAllocationsService getApprovedAllocationsService) {
        this.getApprovedAllocationsService = getApprovedAllocationsService;
    }

    @GetMapping("/approved")
    public List<ApprovedAllocationResponse> getApprovedAllocations() {
        return getApprovedAllocationsService.getApprovedAllocations();
    }
}
