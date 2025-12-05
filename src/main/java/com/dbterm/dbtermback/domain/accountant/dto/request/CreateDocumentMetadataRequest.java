package com.dbterm.dbtermback.domain.accountant.dto.request;

public class CreateDocumentMetadataRequest {

    private Long disbursementId;
    private String storagePath;
    private String fileHash;

    public CreateDocumentMetadataRequest() {
    }

    public Long getDisbursementId() {
        return disbursementId;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setDisbursementId(Long disbursementId) {
        this.disbursementId = disbursementId;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}
