package com.dbterm.dbtermback.domain.auth.repository;

import com.dbterm.dbtermback.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
