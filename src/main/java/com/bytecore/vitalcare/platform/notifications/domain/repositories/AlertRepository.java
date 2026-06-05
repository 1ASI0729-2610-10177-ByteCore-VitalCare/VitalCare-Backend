package com.bytecore.vitalcare.platform.notifications.domain.repositories;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertRepository {
    Optional<Alert> findById(Long id);
    List<Alert> findAll();
    List<Alert> findByUserId(Long userId);
    List<Alert> findByPatientId(Long patientId);
    Alert save(Alert alert);
}
