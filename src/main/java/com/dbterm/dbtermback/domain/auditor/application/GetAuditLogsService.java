package com.dbterm.dbtermback.domain.auditor.application;

import com.dbterm.dbtermback.domain.auditor.dto.response.AuditLogResponse;
import com.dbterm.dbtermback.domain.auditor.entity.AuditLog;
import com.dbterm.dbtermback.domain.auditor.repository.AuditLogRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetAuditLogsService {

    private final AuditLogRepository auditLogRepository;

    public GetAuditLogsService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public Page<AuditLogResponse> getAuditLogs(
            String table,
            LocalDateTime from,
            LocalDateTime to,
            Long actorUserId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "eventTime"));

        LocalDateTime effectiveFrom = (from != null)
                ? from
                : LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);

        LocalDateTime effectiveTo = (to != null)
                ? to
                : LocalDateTime.of(9999, Month.DECEMBER, 31, 23, 59, 59);

        Page<AuditLog> logs = auditLogRepository.findByFilters(
                table,
                effectiveFrom,
                effectiveTo,
                pageable
        );

        return new PageImpl<>(
                logs.stream().map(this::toResponse).collect(Collectors.toList()),
                pageable,
                logs.getTotalElements()
        );
    }

    private AuditLogResponse toResponse(AuditLog log) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(log.getId());
        response.setEventTime(log.getEventTime());
        response.setActorUserId(log.getActorUserId());
        response.setAction(log.getAction());
        response.setObjectTable(log.getObjectTable());
        response.setObjectId(log.getObjectId());
        response.setOldData(log.getOldData());
        response.setNewData(log.getNewData());
        return response;
    }
}
