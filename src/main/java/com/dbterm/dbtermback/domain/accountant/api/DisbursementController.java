package com.dbterm.dbtermback.domain.accountant.api;

import com.dbterm.dbtermback.domain.accountant.application.CreateDisbursementService;
import com.dbterm.dbtermback.domain.accountant.dto.request.CreateDisbursementRequest;
import com.dbterm.dbtermback.domain.accountant.dto.response.DisbursementResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/disbursements")
@PreAuthorize("hasRole('ACCOUNTANT')")
public class DisbursementController {

    private final CreateDisbursementService createDisbursementService;

    public DisbursementController(CreateDisbursementService createDisbursementService) {
        this.createDisbursementService = createDisbursementService;
    }

    @PostMapping
    public DisbursementResponse createDisbursement(@RequestBody CreateDisbursementRequest request) {
        Long accountantUserId = 1L;
        return createDisbursementService.createDisbursement(request, accountantUserId);
    }
}
