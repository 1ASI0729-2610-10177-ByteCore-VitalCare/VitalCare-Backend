package com.bytecore.vitalcare.platform.iam.interfaces.rest.resources;

public record UserPreferenceResource(
        Long users_id,
        String language,
        String fontSize,
        String backgroundColor,
        boolean emailNotifications,
        boolean pushNotifications
) {}
