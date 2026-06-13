package com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources;

import com.bytecore.vitalcare.platform.notifications.domain.model.valueobjects.AlertType;

public record CreateAlertResource(
    AlertType type,
    String description,
    Long userId,
    Long patientId
) {}
