package com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.adapters;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.domain.repositories.AlertRepository;
import com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.assemblers.AlertPersistenceAssembler;
import com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.repositories.AlertPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertPersistenceRepository persistenceRepository;
    private final AlertPersistenceAssembler assembler;

    public AlertRepositoryImpl(AlertPersistenceRepository persistenceRepository, AlertPersistenceAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Alert> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomain);
    }

    @Override
    public List<Alert> findAll() {
        return persistenceRepository.findAll().stream().map(assembler::toDomain).toList();
    }

    @Override
    public List<Alert> findByUserId(Long userId) {
        return persistenceRepository.findByUserId(userId).stream().map(assembler::toDomain).toList();
    }

    @Override
    public List<Alert> findByPatientId(Long patientId) {
        return persistenceRepository.findByPatientId(patientId).stream().map(assembler::toDomain).toList();
    }

    @Override
    public Alert save(Alert alert) {
        var entity = assembler.toEntity(alert);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomain(saved);
    }
}
