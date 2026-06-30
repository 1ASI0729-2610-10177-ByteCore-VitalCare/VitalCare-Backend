package com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources;

public record NotificationResource(
        Long id,
        String nombre,
        String fecha,
        String descripcion,
        Long patientId,
        Long users_id
) {}
