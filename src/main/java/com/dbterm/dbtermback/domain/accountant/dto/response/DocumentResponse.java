package com.dbterm.dbtermback.domain.accountant.dto.response;

import com.dbterm.dbtermback.domain.accountant.entity.Document;

import java.time.OffsetDateTime;

public class DocumentResponse {

    private Long documentId;
    private Long disbursementId;
    private String storagePath;
    private String fileHash;
    private Long uploadedBy;
    private OffsetDateTime uploadedAt;

    public DocumentResponse(Long documentId, Long disbursementId, String storagePath, String fileHash,
                            Long uploadedBy, OffsetDateTime uploadedAt) {
        this.documentId = documentId;
        this.disbursementId = disbursementId;
        this.storagePath = storagePath;
        this.fileHash = fileHash;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    public static DocumentResponse fromEntity(Document document) {
        return new DocumentResponse(
                document.getId(),
                document.getDisbursementId(),
                document.getStoragePath(),
                document.getFileHash(),
                document.getUploadedBy(),
                document.getUploadedAt()
        );
    }

    public Long getDocumentId() {
        return documentId;
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

    public Long getUploadedBy() {
        return uploadedBy;
    }

    public OffsetDateTime getUploadedAt() {
        return uploadedAt;
    }
}
