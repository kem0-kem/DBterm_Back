package com.dbterm.dbtermback.domain.auditor.application;

import com.dbterm.dbtermback.domain.auditor.entity.AuditLog;
import com.dbterm.dbtermback.domain.auditor.repository.AuditLogRepository;
import java.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditLoggingService {

    private final AuditLogRepository auditLogRepository;

    public AuditLoggingService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void record(String action, String objectTable, Long objectId, String oldData, String newData) {
        Long actorUserId = resolveCurrentUserId();
        recordWithActor(actorUserId, action, objectTable, objectId, oldData, newData);
    }

    public void recordWithActor(Long actorUserId, String action, String objectTable, Long objectId, String oldData, String newData) {
        AuditLog log = new AuditLog();
        log.setEventTime(LocalDateTime.now());
        log.setActorUserId(actorUserId);
        log.setAction(action);
        log.setObjectTable(objectTable);
        log.setObjectId(objectId);
        log.setOldData(oldData);
        log.setNewData(newData);
        auditLogRepository.save(log);
    }

    public void recordEntityChange(String action, String objectTable, Long objectId, Object oldEntity, Object newEntity) {
        String oldValue = toStringOrNull(oldEntity);
        String newValue = toStringOrNull(newEntity);
        record(action, objectTable, objectId, oldValue, newValue);
    }

    public void recordEntityChangeWithActor(Long actorUserId, String action, String objectTable, Long objectId, Object oldEntity, Object newEntity) {
        String oldValue = toStringOrNull(oldEntity);
        String newValue = toStringOrNull(newEntity);
        recordWithActor(actorUserId, action, objectTable, objectId, oldValue, newValue);
    }

    private String toStringOrNull(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    private Long resolveCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }
            Object principal = authentication.getPrincipal();

            if (principal instanceof com.dbterm.dbtermback.domain.auth.entity.User) {
                return ((com.dbterm.dbtermback.domain.auth.entity.User) principal).getId();
            }

            try {
                java.lang.reflect.Method getIdMethod = principal.getClass().getMethod("getId");
                Object id = getIdMethod.invoke(principal);
                if (id instanceof Number) {
                    return ((Number) id).longValue();
                }
            } catch (Exception ignored) {
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
