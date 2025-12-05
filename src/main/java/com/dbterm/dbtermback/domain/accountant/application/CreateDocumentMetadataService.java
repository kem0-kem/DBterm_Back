package com.dbterm.dbtermback.domain.accountant.application;

import com.dbterm.dbtermback.domain.accountant.dto.request.CreateDocumentMetadataRequest;
import com.dbterm.dbtermback.domain.accountant.dto.response.DocumentResponse;
import com.dbterm.dbtermback.domain.accountant.entity.Disbursement;
import com.dbterm.dbtermback.domain.accountant.entity.Document;
import com.dbterm.dbtermback.domain.accountant.repository.DisbursementRepository;
import com.dbterm.dbtermback.domain.accountant.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDocumentMetadataService {

    private final DisbursementRepository disbursementRepository;
    private final DocumentRepository documentRepository;

    public CreateDocumentMetadataService(DisbursementRepository disbursementRepository,
                                         DocumentRepository documentRepository) {
        this.disbursementRepository = disbursementRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public DocumentResponse createDocument(CreateDocumentMetadataRequest request, Long accountantUserId) {
        Disbursement disbursement = disbursementRepository.findById(request.getDisbursementId())
                .orElseThrow(() -> new IllegalArgumentException("Disbursement not found: " + request.getDisbursementId()));

        Document document = new Document(
                disbursement.getId(),
                request.getStoragePath(),
                request.getFileHash(),
                accountantUserId
        );
        Document saved = documentRepository.save(document);
        return DocumentResponse.fromEntity(saved);
    }
}
