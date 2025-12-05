package com.dbterm.dbtermback.domain.accountant.repository;

import com.dbterm.dbtermback.domain.accountant.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

    Optional<Disbursement> findByAllocationId(Long allocationId);
}
