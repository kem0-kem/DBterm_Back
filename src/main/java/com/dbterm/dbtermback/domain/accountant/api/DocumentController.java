package com.dbterm.dbtermback.domain.accountant.api;

import com.dbterm.dbtermback.domain.accountant.application.CreateDocumentMetadataService;
import com.dbterm.dbtermback.domain.accountant.dto.request.CreateDocumentMetadataRequest;
import com.dbterm.dbtermback.domain.accountant.dto.response.DocumentResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@PreAuthorize("hasRole('ACCOUNTANT')")
public class DocumentController {

    private final CreateDocumentMetadataService createDocumentMetadataService;

    public DocumentController(CreateDocumentMetadataService createDocumentMetadataService) {
        this.createDocumentMetadataService = createDocumentMetadataService;
    }

    @PostMapping
    public DocumentResponse createDocument(@RequestBody CreateDocumentMetadataRequest request) {
        Long accountantUserId = 1L;
        return createDocumentMetadataService.createDocument(request, accountantUserId);
    }
}
