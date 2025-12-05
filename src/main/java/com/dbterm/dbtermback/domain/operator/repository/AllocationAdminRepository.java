package com.dbterm.dbtermback.domain.operator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AllocationAdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Long> findPendingAllocationIdsByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        Set<Long> uniqueIds = new HashSet<>(ids);
        List<?> rows = entityManager.createNativeQuery(
                        "SELECT allocation_id FROM allocation WHERE allocation_id IN (:ids) AND status = 'PENDING'")
                .setParameter("ids", uniqueIds)
                .getResultList();
        List<Long> result = new ArrayList<>();
        for (Object row : rows) {
            if (row instanceof Number) {
                result.add(((Number) row).longValue());
            }
        }
        return result;
    }

    @Transactional
    public int approvePendingAllocations(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return entityManager.createNativeQuery(
                        "UPDATE allocation SET status = 'APPROVED' WHERE allocation_id IN (:ids) AND status = 'PENDING'")
                .setParameter("ids", ids)
                .executeUpdate();
    }
}
