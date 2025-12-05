package com.dbterm.dbtermback.domain.donor.repository;

import java.time.LocalDateTime;

public interface DonationTraceRow {
    Long getDonationId();
    Double getDonationAmount();
    Boolean getDonationVerified();
    LocalDateTime getDonationDonatedAt();

    Long getCampaignId();
    String getCampaignTitle();

    Long getAllocationId();
    Double getAllocationAmount();
    String getAllocationStatus();

    Long getReceiverId();
    String getReceiverName();
    String getReceiverType();

    Long getDisbursementId();
    Double getDisbursementAmount();
    String getDisbursementStatus();
    String getPaymentTxRef();

    Long getDonorId();
    String getDonorUsername();
    Boolean getDonorIsAnonymous();
}
