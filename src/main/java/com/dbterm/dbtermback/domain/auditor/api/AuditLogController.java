package com.dbterm.dbtermback.domain.auditor.api;

import com.dbterm.dbtermback.domain.auditor.application.GetAuditLogsService;
import com.dbterm.dbtermback.domain.auditor.dto.response.AuditLogResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditLogController {

    private final GetAuditLogsService getAuditLogsService;

    public AuditLogController(GetAuditLogsService getAuditLogsService) {
        this.getAuditLogsService = getAuditLogsService;
    }

    @GetMapping("/api/audit/logs")
    public Page<AuditLogResponse> getAuditLogs(
            @RequestParam(name = "table", required = false) String table,
            @RequestParam(name = "dateFrom", required = false) String dateFrom,
            @RequestParam(name = "dateTo", required = false) String dateTo,
            @RequestParam(name = "actorUserId", required = false) Long actorUserId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LocalDateTime from = parseDate(dateFrom, true);
        LocalDateTime to = parseDate(dateTo, false);
        return getAuditLogsService.getAuditLogs(table, from, to, actorUserId, page, size);
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
