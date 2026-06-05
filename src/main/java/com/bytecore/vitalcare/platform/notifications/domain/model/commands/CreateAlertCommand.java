package com.bytecore.vitalcare.platform.notifications.domain.model.commands;

import com.bytecore.vitalcare.platform.notifications.domain.model.valueobjects.AlertType;

public record CreateAlertCommand(AlertType type, String description, Long userId, Long patientId) {
}
