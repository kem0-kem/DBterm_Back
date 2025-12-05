package com.dbterm.dbtermback.domain.accountant.dto.response;

import com.dbterm.dbtermback.domain.accountant.entity.Disbursement;
import com.dbterm.dbtermback.domain.accountant.entity.DisbursementStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class DisbursementResponse {

    private Long disbursementId;
    private Long allocationId;
    private Long executedBy;
    private OffsetDateTime executedAt;
    private BigDecimal amount;
    private DisbursementStatus status;
    private String paymentTxRef;

    public DisbursementResponse(Long disbursementId, Long allocationId, Long executedBy, OffsetDateTime executedAt,
                                BigDecimal amount, DisbursementStatus status, String paymentTxRef) {
        this.disbursementId = disbursementId;
        this.allocationId = allocationId;
        this.executedBy = executedBy;
        this.executedAt = executedAt;
        this.amount = amount;
        this.status = status;
        this.paymentTxRef = paymentTxRef;
    }

    public static DisbursementResponse fromEntity(Disbursement disbursement) {
        return new DisbursementResponse(
                disbursement.getId(),
                disbursement.getAllocationId(),
                disbursement.getExecutedBy(),
                disbursement.getExecutedAt(),
                disbursement.getAmount(),
                disbursement.getStatus(),
                disbursement.getPaymentTxRef()
        );
    }

    public Long getDisbursementId() {
        return disbursementId;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public Long getExecutedBy() {
        return executedBy;
    }

    public OffsetDateTime getExecutedAt() {
        return executedAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DisbursementStatus getStatus() {
        return status;
    }

    public String getPaymentTxRef() {
        return paymentTxRef;
    }
}
