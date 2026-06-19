package com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertPersistenceRepository extends JpaRepository<AlertPersistenceEntity, Long> {
    List<AlertPersistenceEntity> findByUserId(Long userId);
    List<AlertPersistenceEntity> findByPatientId(Long patientId);
}
