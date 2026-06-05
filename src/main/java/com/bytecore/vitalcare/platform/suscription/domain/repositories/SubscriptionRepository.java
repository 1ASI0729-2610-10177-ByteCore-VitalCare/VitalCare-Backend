package com.bytecore.vitalcare.platform.suscription.domain.repositories;

import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(Long id);
    Optional<Subscription> findByUserId(Long userId);
    List<Subscription> findAll();
    Subscription save(Subscription subscription);
    boolean existsByUserId(Long userId);
}
