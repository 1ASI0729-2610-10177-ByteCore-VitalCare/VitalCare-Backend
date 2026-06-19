package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateVitalSignCommand(
        Long id,
        Long patchId,
        LocalDateTime recordedAt,
        BigDecimal glucoseLevel,
        BigDecimal lactateConcentration,
        BigDecimal alcoholLevel,
        BigDecimal ketones,
        BigDecimal bloodPressure,
        BigDecimal temperature,
        BigDecimal oxygenSaturation,
        BigDecimal sodiumPotassium,
        BigDecimal heartRate,
        BigDecimal cytokines,
        BigDecimal tCells,
        BigDecimal humidity,
        BigDecimal atmosphericPressure,
        BigDecimal airQuality
) {
}
