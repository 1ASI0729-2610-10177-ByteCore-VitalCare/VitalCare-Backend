package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import java.math.BigDecimal;

public record RecordLocationCommand(Long patchId, BigDecimal latitude, BigDecimal longitude) {
}
