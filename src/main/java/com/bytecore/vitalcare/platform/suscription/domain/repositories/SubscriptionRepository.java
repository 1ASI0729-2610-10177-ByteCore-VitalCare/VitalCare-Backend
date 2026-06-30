package com.bytecore.vitalcare.platform.suscription.domain.repositories;

import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserId(Long userId);
    Optional<Subscription> findFirstByUserIdAndStatus(Long userId, SubscriptionStatus status);
    List<Subscription> findAllByUserId(Long userId);
    boolean existsByUserId(Long userId);
}