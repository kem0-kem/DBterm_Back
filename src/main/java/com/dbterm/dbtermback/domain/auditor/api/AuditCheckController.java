package com.dbterm.dbtermback.domain.auditor.api;

import com.dbterm.dbtermback.domain.auditor.application.GetAllocationDisbursementMismatchService;
import com.dbterm.dbtermback.domain.auditor.application.GetDocumentHashesService;
import com.dbterm.dbtermback.domain.auditor.dto.response.AllocationDisbursementMismatchResponse;
import com.dbterm.dbtermback.domain.auditor.dto.response.DocumentHashResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditCheckController {

    private final GetAllocationDisbursementMismatchService getAllocationDisbursementMismatchService;
    private final GetDocumentHashesService getDocumentHashesService;

    public AuditCheckController(
            GetAllocationDisbursementMismatchService getAllocationDisbursementMismatchService,
            GetDocumentHashesService getDocumentHashesService
    ) {
        this.getAllocationDisbursementMismatchService = getAllocationDisbursementMismatchService;
        this.getDocumentHashesService = getDocumentHashesService;
    }

    @GetMapping("/api/audit/mismatch")
    public List<AllocationDisbursementMismatchResponse> getMismatches() {
        return getAllocationDisbursementMismatchService.getMismatches();
    }

    @GetMapping("/api/documents/hashes")
    public List<DocumentHashResponse> getDocumentHashes(
            @RequestParam(name = "dateFrom", required = false) String dateFrom,
            @RequestParam(name = "dateTo", required = false) String dateTo
    ) {
        LocalDateTime from = parseDate(dateFrom, true);
        LocalDateTime to = parseDate(dateTo, false);
        return getDocumentHashesService.getDocumentHashes(from, to);
    }

    private LocalDateTime parseDate(String value, boolean startOfDay) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        LocalDate date = LocalDate.parse(value);
        if (startOfDay) {
            return date.atStartOfDay();
        }
        return LocalDateTime.of(date, LocalTime.MAX);
    }
}
