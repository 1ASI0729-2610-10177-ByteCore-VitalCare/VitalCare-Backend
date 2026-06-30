package com.bytecore.vitalcare.platform.patients.interfaces.rest.resources;

public record CreatePatchResource(
        String patch_code,
        String status,
        Long patients_id
) {}
