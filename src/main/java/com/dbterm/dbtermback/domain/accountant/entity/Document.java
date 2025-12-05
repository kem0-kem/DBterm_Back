package com.dbterm.dbtermback.domain.accountant.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @Column(name = "disbursement_id", nullable = false)
    private Long disbursementId;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "file_hash", nullable = false)
    private String fileHash;

    @Column(name = "uploaded_by", nullable = false)
    private Long uploadedBy;

    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private OffsetDateTime uploadedAt;

    protected Document() {
    }

    public Document(Long disbursementId, String storagePath, String fileHash, Long uploadedBy) {
        this.disbursementId = disbursementId;
        this.storagePath = storagePath;
        this.fileHash = fileHash;
        this.uploadedBy = uploadedBy;
    }

    public Long getId() {
        return id;
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
