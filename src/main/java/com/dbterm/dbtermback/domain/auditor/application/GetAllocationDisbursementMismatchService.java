package com.dbterm.dbtermback.domain.auditor.application;

import com.dbterm.dbtermback.domain.auditor.dto.response.AllocationDisbursementMismatchResponse;
import com.dbterm.dbtermback.domain.auditor.repository.AuditorCheckRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetAllocationDisbursementMismatchService {

    private final AuditorCheckRepository auditorCheckRepository;

    public GetAllocationDisbursementMismatchService(AuditorCheckRepository auditorCheckRepository) {
        this.auditorCheckRepository = auditorCheckRepository;
    }

    public List<AllocationDisbursementMismatchResponse> getMismatches() {
        List<Object[]> rows = auditorCheckRepository.findAllocationDisbursementMismatches();
        List<AllocationDisbursementMismatchResponse> result = new ArrayList<>();
        for (Object[] row : rows) {
            AllocationDisbursementMismatchResponse response = new AllocationDisbursementMismatchResponse();
            response.setAllocationId(row[0] != null ? ((Number) row[0]).longValue() : null);
            response.setDisbursementId(row[1] != null ? ((Number) row[1]).longValue() : null);
            response.setAllocationAmount(row[2] != null ? (BigDecimal) row[2] : null);
            response.setDisbursementAmount(row[3] != null ? (BigDecimal) row[3] : null);
            response.setCampaignId(row[4] != null ? ((Number) row[4]).longValue() : null);
            response.setCampaignTitle(row[5] != null ? row[5].toString() : null);
            response.setDisbursementStatus(row[6] != null ? row[6].toString() : null);
            result.add(response);
        }
        return result;
    }
}
