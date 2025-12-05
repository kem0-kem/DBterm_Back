package com.dbterm.dbtermback.domain.accountant.repository;

import com.dbterm.dbtermback.domain.accountant.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
