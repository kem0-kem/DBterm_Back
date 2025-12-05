package com.dbterm.dbtermback.domain.accountant.dto.request;

public class CreateReceiverRequest {

    private String name;
    private String type;
    private String bankAccount;

    public CreateReceiverRequest() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
