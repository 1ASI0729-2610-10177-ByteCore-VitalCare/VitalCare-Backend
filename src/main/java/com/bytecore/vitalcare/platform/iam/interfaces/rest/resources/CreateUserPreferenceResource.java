package com.bytecore.vitalcare.platform.iam.interfaces.rest.resources;

public record CreateUserPreferenceResource(
        Long users_id,
        String language,
        String fontSize,
        String backgroundColor,
        boolean emailNotifications,
        boolean pushNotifications
) {}
