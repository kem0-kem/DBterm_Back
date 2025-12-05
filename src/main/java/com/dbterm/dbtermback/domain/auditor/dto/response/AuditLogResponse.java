package com.dbterm.dbtermback.domain.auditor.dto.response;

import java.time.LocalDateTime;

public class AuditLogResponse {

    private Long id;
    private LocalDateTime eventTime;
    private Long actorUserId;
    private String action;
    private String objectTable;
    private Long objectId;
    private String oldData;
    private String newData;

    public AuditLogResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Long getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(Long actorUserId) {
        this.actorUserId = actorUserId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjectTable() {
        return objectTable;
    }

    public void setObjectTable(String objectTable) {
        this.objectTable = objectTable;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }
}
