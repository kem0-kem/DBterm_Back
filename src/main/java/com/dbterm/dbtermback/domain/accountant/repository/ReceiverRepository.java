package com.dbterm.dbtermback.domain.accountant.repository;

import com.dbterm.dbtermback.domain.accountant.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
    Optional<Receiver> findFirstByNameAndType(String name, String type);
}
