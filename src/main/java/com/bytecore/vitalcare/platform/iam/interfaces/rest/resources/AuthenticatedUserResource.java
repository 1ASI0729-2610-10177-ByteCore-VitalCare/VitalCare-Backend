package com.bytecore.vitalcare.platform.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(
        Long id,
        String name,
        String email,
        String token
) {}
