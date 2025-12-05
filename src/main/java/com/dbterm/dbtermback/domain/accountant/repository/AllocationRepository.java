package com.dbterm.dbtermback.domain.accountant.repository;

import com.dbterm.dbtermback.domain.accountant.entity.Allocation;
import com.dbterm.dbtermback.domain.accountant.entity.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    boolean existsByCampaignId(Long campaignId);

    List<Allocation> findByStatus(AllocationStatus status);
}
