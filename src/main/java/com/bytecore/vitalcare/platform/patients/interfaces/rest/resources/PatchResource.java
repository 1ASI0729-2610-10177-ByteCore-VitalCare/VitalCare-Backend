package com.bytecore.vitalcare.platform.patients.interfaces.rest.resources;

public record PatchResource(
        Long id,
        String patch_code,
        String linked_at,
        String status,
        Long patients_id
) {}
