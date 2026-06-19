package com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {

    public static AlertResource toResourceFromEntity(Alert alert) {
        return new AlertResource(
            alert.getId(),
            alert.getType().name(),
            alert.getDescription(),
            alert.isRead(),
            alert.getUserId(),
            alert.getPatientId()
        );
    }
}
