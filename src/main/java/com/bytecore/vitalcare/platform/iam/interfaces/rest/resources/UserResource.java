package com.bytecore.vitalcare.platform.iam.interfaces.rest.resources;

public record UserResource(
        Long id,
        String name,
        String email,
        String created_at
) {}
