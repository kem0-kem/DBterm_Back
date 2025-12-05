package com.dbterm.dbtermback.domain.auditor.repository;

import com.dbterm.dbtermback.domain.auditor.entity.AuditLog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Query("""
        select a
        from AuditLog a
        where (:table is null or a.objectTable = :table)
          and a.eventTime between :from and :to
        order by a.eventTime desc
        """)
    Page<AuditLog> findByFilters(
            @Param("table") String table,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable
    );
}
