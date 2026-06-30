package com.bytecore.vitalcare.platform.notifications.domain.model.commands;

public record CreateNotificationCommand(
        String nombre,
        String fecha,
        String descripcion,
        Long patientId,
        Long usersId
) {}
