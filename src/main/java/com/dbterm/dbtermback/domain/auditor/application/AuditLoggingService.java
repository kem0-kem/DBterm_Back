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
            if (principal == null) {
                return null;
            }

            // 1) principal이 User 엔티티인 경우
            if (principal instanceof com.dbterm.dbtermback.domain.auth.entity.User user) {
                return user.getId();
            }

            // 2) principal이 숫자 타입(Long, Integer 등)인 경우
            if (principal instanceof Number n) {
                return n.longValue();
            }

            // 3) principal이 "1", "2" 같은 문자열인 경우
            if (principal instanceof String s) {
                try {
                    return Long.parseLong(s);
                } catch (NumberFormatException ignored) {
                    // username 같은 경우는 여기서 걸릴 수 있음 → 무시하고 아래로
                }
            }

            // 4) principal에 getId() 메서드가 있는 커스텀 타입인 경우 (기존 로직 유지)
            try {
                java.lang.reflect.Method getIdMethod = principal.getClass().getMethod("getId");
                Object id = getIdMethod.invoke(principal);
                if (id instanceof Number n) {
                    return n.longValue();
                }
            } catch (Exception ignored) {
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
