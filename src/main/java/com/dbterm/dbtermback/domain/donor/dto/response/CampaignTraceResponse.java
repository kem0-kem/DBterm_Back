package com.dbterm.dbtermback.domain.donor.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CampaignTraceResponse {

    private Long campaignId;
    private String campaignTitle;
    private List<CampaignTraceResponse> donations = new ArrayList<>();
    private Double totalDonationAmount;
    private Double totalDisbursedAmount;

    private Long donationId;
    private Double donationAmount;
    private Boolean donationVerified;
    private LocalDateTime donatedAt;

    private Long donorId;
    private Boolean donorAnonymous;
    private String donorName;

    private CampaignInfo campaign;
    private List<AllocationInfo> allocations = new ArrayList<>();

    public CampaignTraceResponse() {}

    public CampaignTraceResponse(Long donationId, Double amount, Boolean verified, LocalDateTime donatedAt) {
        this.donationId = donationId;
        this.donationAmount = amount;
        this.donationVerified = verified;
        this.donatedAt = donatedAt;
    }

    // -------- Setter & Getter --------
    public Long getCampaignId() { return campaignId; }
    public void setCampaignId(Long campaignId) { this.campaignId = campaignId; }

    public String getCampaignTitle() { return campaignTitle; }
    public void setCampaignTitle(String campaignTitle) { this.campaignTitle = campaignTitle; }

    public List<CampaignTraceResponse> getDonations() { return donations; }
    public void setDonations(List<CampaignTraceResponse> donations) { this.donations = donations; }

    public Double getTotalDonationAmount() { return totalDonationAmount; }
    public void setTotalDonationAmount(Double totalDonationAmount) { this.totalDonationAmount = totalDonationAmount; }

    public Double getTotalDisbursedAmount() { return totalDisbursedAmount; }
    public void setTotalDisbursedAmount(Double totalDisbursedAmount) { this.totalDisbursedAmount = totalDisbursedAmount; }

    public Long getDonationId() { return donationId; }
    public void setDonationId(Long donationId) { this.donationId = donationId; }

    public Double getDonationAmount() { return donationAmount; }
    public void setDonationAmount(Double donationAmount) { this.donationAmount = donationAmount; }

    public Boolean getDonationVerified() { return donationVerified; }
    public void setDonationVerified(Boolean donationVerified) { this.donationVerified = donationVerified; }

    public LocalDateTime getDonatedAt() { return donatedAt; }
    public void setDonatedAt(LocalDateTime donatedAt) { this.donatedAt = donatedAt; }

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public Boolean getDonorAnonymous() { return donorAnonymous; }
    public void setDonorAnonymous(Boolean donorAnonymous) { this.donorAnonymous = donorAnonymous; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public CampaignInfo getCampaign() { return campaign; }
    public void setCampaign(CampaignInfo campaign) { this.campaign = campaign; }

    public List<AllocationInfo> getAllocations() { return allocations; }
    public void setAllocations(List<AllocationInfo> allocations) { this.allocations = allocations; }


    // ---- Inner Classes ----
    public static class CampaignInfo {
        private Long campaignId;
        private String campaignTitle;

        public CampaignInfo(Long campaignId, String campaignTitle) {
            this.campaignId = campaignId;
            this.campaignTitle = campaignTitle;
        }

        public Long getCampaignId() { return campaignId; }
        public void setCampaignId(Long campaignId) { this.campaignId = campaignId; }

        public String getCampaignTitle() { return campaignTitle; }
        public void setCampaignTitle(String campaignTitle) { this.campaignTitle = campaignTitle; }
    }

    public static class AllocationInfo {
        private Long allocationId;
        private Double allocationAmount;
        private String allocationStatus;
        private ReceiverInfo receiver;
        private DisbursementInfo disbursement;

        public AllocationInfo(Long allocationId, Double allocationAmount, String status) {
            this.allocationId = allocationId;
            this.allocationAmount = allocationAmount;
            this.allocationStatus = status;
        }

        public Long getAllocationId() { return allocationId; }
        public void setAllocationId(Long allocationId) { this.allocationId = allocationId; }

        public Double getAllocationAmount() { return allocationAmount; }
        public void setAllocationAmount(Double allocationAmount) { this.allocationAmount = allocationAmount; }

        public String getAllocationStatus() { return allocationStatus; }
        public void setAllocationStatus(String allocationStatus) { this.allocationStatus = allocationStatus; }

        public ReceiverInfo getReceiver() { return receiver; }
        public void setReceiver(ReceiverInfo receiver) { this.receiver = receiver; }

        public DisbursementInfo getDisbursement() { return disbursement; }
        public void setDisbursement(DisbursementInfo disbursement) { this.disbursement = disbursement; }
    }

    public static class ReceiverInfo {
        private Long receiverId;
        private String receiverName;
        private String receiverType;

        public ReceiverInfo(Long id, String name, String type) {
            this.receiverId = id;
            this.receiverName = name;
            this.receiverType = type;
        }

        public Long getReceiverId() { return receiverId; }
        public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

        public String getReceiverName() { return receiverName; }
        public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

        public String getReceiverType() { return receiverType; }
        public void setReceiverType(String receiverType) { this.receiverType = receiverType; }
    }

    public static class DisbursementInfo {
        private Long disbursementId;
        private Double disbursementAmount;
        private String disbursementStatus;
        private String paymentTxRef;

        public DisbursementInfo(Long id, Double amount, String status, String txRef) {
            this.disbursementId = id;
            this.disbursementAmount = amount;
            this.disbursementStatus = status;
            this.paymentTxRef = txRef;
        }

        public Long getDisbursementId() { return disbursementId; }
        public void setDisbursementId(Long disbursementId) { this.disbursementId = disbursementId; }

        public Double getDisbursementAmount() { return disbursementAmount; }
        public void setDisbursementAmount(Double disbursementAmount) { this.disbursementAmount = disbursementAmount; }

        public String getDisbursementStatus() { return disbursementStatus; }
        public void setDisbursementStatus(String disbursementStatus) { this.disbursementStatus = disbursementStatus; }

        public String getPaymentTxRef() { return paymentTxRef; }
        public void setPaymentTxRef(String paymentTxRef) { this.paymentTxRef = paymentTxRef; }
    }
}
