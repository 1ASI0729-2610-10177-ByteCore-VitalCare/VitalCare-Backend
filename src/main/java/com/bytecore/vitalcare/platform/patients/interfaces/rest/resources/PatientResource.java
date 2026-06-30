package com.bytecore.vitalcare.platform.patients.interfaces.rest.resources;

public record PatientResource(
        Long id,
        String name,
        String birth_date,
        String gender,
        String photo,
        Long users_id
) {}
