package com.dbterm.dbtermback.domain.accountant.dto.response;

import com.dbterm.dbtermback.domain.accountant.entity.Receiver;

import java.time.OffsetDateTime;

public class ReceiverResponse {

    private Long receiverId;
    private String name;
    private String type;
    private String bankAccount;
    private OffsetDateTime createdAt;

    public ReceiverResponse(Long receiverId, String name, String type, String bankAccount, OffsetDateTime createdAt) {
        this.receiverId = receiverId;
        this.name = name;
        this.type = type;
        this.bankAccount = bankAccount;
        this.createdAt = createdAt;
    }

    public static ReceiverResponse fromEntity(Receiver receiver) {
        return new ReceiverResponse(
                receiver.getId(),
                receiver.getName(),
                receiver.getType(),
                receiver.getBankAccount(),
                receiver.getCreatedAt()
        );
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
