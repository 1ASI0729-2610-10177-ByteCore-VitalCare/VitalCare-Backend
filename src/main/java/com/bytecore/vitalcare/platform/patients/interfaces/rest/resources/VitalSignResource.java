package com.bytecore.vitalcare.platform.patients.interfaces.rest.resources;

import java.math.BigDecimal;

public record VitalSignResource(
        Long id,
        String recorded_at,
        BigDecimal glucose_level,
        BigDecimal lactate_concentration,
        BigDecimal alcohol_level,
        BigDecimal ketones,
        BigDecimal blood_pressure,
        BigDecimal temperature,
        BigDecimal oxygen_saturation,
        BigDecimal sodium_potassium,
        BigDecimal heart_rate,
        BigDecimal cytokines,
        BigDecimal t_cells,
        BigDecimal humidity,
        BigDecimal atmospheric_pressure,
        BigDecimal air_quality,
        Long patches_id
) {}
