package com.dbterm.dbtermback.domain.accountant.dto.request;

import java.math.BigDecimal;

public class CreateDisbursementRequest {

    private Long allocationId;
    private BigDecimal amount;
    private String paymentTxRef;

    public CreateDisbursementRequest() {
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentTxRef() {
        return paymentTxRef;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentTxRef(String paymentTxRef) {
        this.paymentTxRef = paymentTxRef;
    }
}
