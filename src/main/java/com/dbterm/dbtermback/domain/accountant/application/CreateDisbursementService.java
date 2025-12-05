package com.dbterm.dbtermback.domain.accountant.application;

import com.dbterm.dbtermback.domain.accountant.dto.request.CreateDisbursementRequest;
import com.dbterm.dbtermback.domain.accountant.dto.response.DisbursementResponse;
import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.Disbursement;
import com.dbterm.dbtermback.domain.accountant.repository.AllocationRepository;
import com.dbterm.dbtermback.domain.accountant.repository.DisbursementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CreateDisbursementService {

    private final AllocationRepository allocationRepository;
    private final DisbursementRepository disbursementRepository;

    public CreateDisbursementService(AllocationRepository allocationRepository,
                                     DisbursementRepository disbursementRepository) {
        this.allocationRepository = allocationRepository;
        this.disbursementRepository = disbursementRepository;
    }

    @Transactional
    public DisbursementResponse createDisbursement(CreateDisbursementRequest request, Long accountantUserId) {
        Allocation allocation = allocationRepository.findById(request.getAllocationId())
                .orElseThrow(() -> new IllegalArgumentException("Allocation not found: " + request.getAllocationId()));

        disbursementRepository.findByAllocationId(allocation.getId())
                .ifPresent(existing -> {
                    throw new IllegalStateException("Disbursement already exists for allocation: " + allocation.getId());
                });

        BigDecimal amount = allocation.getAmount() != null
                ? BigDecimal.valueOf(allocation.getAmount())
                : null;

        Disbursement disbursement = new Disbursement(
                allocation.getId(),
                accountantUserId,
                amount,
                request.getPaymentTxRef()
        );
        Disbursement saved = disbursementRepository.save(disbursement);
        return DisbursementResponse.fromEntity(saved);
    }
}
