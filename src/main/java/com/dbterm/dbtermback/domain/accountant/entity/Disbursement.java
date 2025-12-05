package com.dbterm.dbtermback.domain.accountant.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "disbursement")
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disbursement_id")
    private Long id;

    @Column(name = "allocation_id", nullable = false)
    private Long allocationId;

    @Column(name = "executed_by", nullable = false)
    private Long executedBy;

    @Column(name = "executed_at", insertable = false, updatable = false)
    private OffsetDateTime executedAt;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DisbursementStatus status;

    @Column(name = "payment_tx_ref", length = 255)
    private String paymentTxRef;

    protected Disbursement() {
    }

    public Disbursement(Long allocationId, Long executedBy, BigDecimal amount, String paymentTxRef) {
        this.allocationId = allocationId;
        this.executedBy = executedBy;
        this.amount = amount;
        this.status = DisbursementStatus.PENDING;
        this.paymentTxRef = paymentTxRef;
    }

    public Long getId() {
        return id;
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

    public void setStatus(DisbursementStatus status) {
        this.status = status;
    }
}
