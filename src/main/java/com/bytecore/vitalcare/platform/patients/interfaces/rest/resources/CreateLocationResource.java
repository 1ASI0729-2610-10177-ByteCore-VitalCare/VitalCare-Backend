package com.bytecore.vitalcare.platform.patients.interfaces.rest.resources;

import java.math.BigDecimal;

public record CreateLocationResource(
        Long patches_id,
        BigDecimal latitude,
        BigDecimal longitude
) {}
