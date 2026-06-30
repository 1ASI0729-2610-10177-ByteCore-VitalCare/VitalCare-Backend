package com.bytecore.vitalcare.platform.patients.interfaces.rest.resources;

import java.math.BigDecimal;

public record LocationResource(
        Long id,
        BigDecimal latitude,
        BigDecimal longitude,
        String recorded_at,
        Long patches_id
) {}
