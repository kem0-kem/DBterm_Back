package com.dbterm.dbtermback.domain.auditor.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AuditorCheckRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> findAllocationDisbursementMismatches() {
        String sql = "select a.allocation_id, d.disbursement_id, a.amount, d.amount, a.campaign_id, c.title, d.status from allocation a join campaign c on c.campaign_id = a.campaign_id left join disbursement d on d.allocation_id = a.allocation_id where (d.disbursement_id is null and a.status = 'APPROVED') or (d.disbursement_id is not null and d.amount <> a.amount)";
        Query query = entityManager.createNativeQuery(sql);
        List<?> result = query.getResultList();
        List<Object[]> rows = new ArrayList<>();
        for (Object row : result) {
            rows.add((Object[]) row);
        }
        return rows;
    }

    public List<Object[]> findDocumentHashes(LocalDateTime from, LocalDateTime to) {
        StringBuilder sb = new StringBuilder();
        sb.append("select d.document_id, d.disbursement_id, d.file_hash, d.uploaded_at, d.uploaded_by from document d");
        List<String> conditions = new ArrayList<>();
        if (from != null) {
            conditions.add("d.uploaded_at >= :from");
        }
        if (to != null) {
        conditions.add("d.uploaded_at <= :to");
        }
        if (!conditions.isEmpty()) {
            sb.append(" where ");
            sb.append(String.join(" and ", conditions));
        }
        sb.append(" order by d.uploaded_at desc");
        Query query = entityManager.createNativeQuery(sb.toString());
        if (from != null) {
            query.setParameter("from", from);
        }
        if (to != null) {
            query.setParameter("to", to);
        }
        List<?> result = query.getResultList();
        List<Object[]> rows = new ArrayList<>();
        for (Object row : result) {
            rows.add((Object[]) row);
        }
        return rows;
    }
}
