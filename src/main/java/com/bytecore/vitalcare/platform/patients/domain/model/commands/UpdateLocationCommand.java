package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateLocationCommand(
        Long id,
        Long patchId,
        BigDecimal latitude,
        BigDecimal longitude,
        LocalDateTime recordedAt
) {
}
