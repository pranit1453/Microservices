package com.demo.auth.repository;

import com.demo.auth.model.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthJpaRepository extends JpaRepository<AuthUser, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
