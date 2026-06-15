package com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
