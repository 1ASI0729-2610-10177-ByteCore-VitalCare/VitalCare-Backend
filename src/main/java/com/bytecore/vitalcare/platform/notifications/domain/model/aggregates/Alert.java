package com.bytecore.vitalcare.platform.notifications.domain.model.aggregates;

import com.bytecore.vitalcare.platform.notifications.domain.model.valueobjects.AlertType;
import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Alert extends AbstractDomainAggregateRoot<Alert> {

    @Setter
    private Long id;
    @Setter
    private AlertType type;
    @Setter
    private String description;
    @Setter
    private boolean isRead;
    @Setter
    private Long userId;
    @Setter
    private Long patientId;

    public Alert() {}

    public Alert(AlertType type, String description, Long userId, Long patientId) {
        this.type = type;
        this.description = description;
        this.isRead = false;
        this.userId = userId;
        this.patientId = patientId;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
