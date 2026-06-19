package com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.assemblers;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertPersistenceAssembler {

    public AlertPersistenceEntity toEntity(Alert alert) {
        return AlertPersistenceEntity.fromDomain(alert);
    }

    public Alert toDomain(AlertPersistenceEntity entity) {
        return entity.toDomain();
    }
}
