package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import java.math.BigDecimal;

public record RecordVitalSignCommand(
        Long patchId,
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
