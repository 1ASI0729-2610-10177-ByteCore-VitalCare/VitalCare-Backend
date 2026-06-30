package com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources;

public record CreateNotificationResource(
        String nombre,
        String fecha,
        String descripcion,
        Long patientId,
        Long users_id
) {}
