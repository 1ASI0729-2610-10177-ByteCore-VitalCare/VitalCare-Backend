package com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources;

public record AlertResource(
    Long id,
    String type,
    String description,
    boolean isRead,
    Long userId,
    Long patientId
) {}
