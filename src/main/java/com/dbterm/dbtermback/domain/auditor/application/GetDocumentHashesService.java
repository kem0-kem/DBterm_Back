package com.dbterm.dbtermback.domain.auditor.application;

import com.dbterm.dbtermback.domain.auditor.dto.response.DocumentHashResponse;
import com.dbterm.dbtermback.domain.auditor.repository.AuditorCheckRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetDocumentHashesService {

    private final AuditorCheckRepository auditorCheckRepository;

    public GetDocumentHashesService(AuditorCheckRepository auditorCheckRepository) {
        this.auditorCheckRepository = auditorCheckRepository;
    }

    public List<DocumentHashResponse> getDocumentHashes(LocalDateTime from, LocalDateTime to) {
        List<Object[]> rows = auditorCheckRepository.findDocumentHashes(from, to);
        List<DocumentHashResponse> result = new ArrayList<>();
        for (Object[] row : rows) {
            DocumentHashResponse response = new DocumentHashResponse();
            response.setDocumentId(row[0] != null ? ((Number) row[0]).longValue() : null);
            response.setDisbursementId(row[1] != null ? ((Number) row[1]).longValue() : null);
            response.setFileHash(row[2] != null ? row[2].toString() : null);
            response.setUploadedAt(row[3] != null ? (LocalDateTime) row[3] : null);
            response.setUploadedBy(row[4] != null ? ((Number) row[4]).longValue() : null);
            result.add(response);
        }
        return result;
    }
}
