package com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.entities;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.domain.model.valueobjects.AlertType;
import com.bytecore.vitalcare.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
public class AlertPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    public AlertPersistenceEntity(AlertType type, String description, boolean isRead, Long userId, Long patientId) {
        this.type = type;
        this.description = description;
        this.isRead = isRead;
        this.userId = userId;
        this.patientId = patientId;
    }

    public Alert toDomain() {
        var alert = new Alert(type, description, userId, patientId);
        alert.setId(getId());
        alert.setRead(isRead);
        return alert;
    }

    public static AlertPersistenceEntity fromDomain(Alert alert) {
        var entity = new AlertPersistenceEntity(
            alert.getType(),
            alert.getDescription(),
            alert.isRead(),
            alert.getUserId(),
            alert.getPatientId()
        );
        if (alert.getId() != null) {
            entity.setId(alert.getId());
        }
        return entity;
    }
}
