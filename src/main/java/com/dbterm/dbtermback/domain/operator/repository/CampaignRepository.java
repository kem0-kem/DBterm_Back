package com.dbterm.dbtermback.domain.operator.repository;

import com.dbterm.dbtermback.domain.operator.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
