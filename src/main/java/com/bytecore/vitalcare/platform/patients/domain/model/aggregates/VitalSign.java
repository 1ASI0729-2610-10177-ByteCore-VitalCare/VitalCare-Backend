package com.bytecore.vitalcare.platform.patients.domain.model.aggregates;

import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class VitalSign extends AbstractDomainAggregateRoot<VitalSign> {

    @Setter private Long id;
    @Setter private LocalDateTime recordedAt;
    @Setter private BigDecimal glucoseLevel;
    @Setter private BigDecimal lactateConcentration;
    @Setter private BigDecimal alcoholLevel;
    @Setter private BigDecimal ketones;
    @Setter private BigDecimal bloodPressure;
    @Setter private BigDecimal temperature;
    @Setter private BigDecimal oxygenSaturation;
    @Setter private BigDecimal sodiumPotassium;
    @Setter private BigDecimal heartRate;
    @Setter private BigDecimal cytokines;
    @Setter private BigDecimal tCells;
    @Setter private BigDecimal humidity;
    @Setter private BigDecimal atmosphericPressure;
    @Setter private BigDecimal airQuality;
    @Setter private Long patchId;

    public VitalSign() {}

    public VitalSign(Long patchId) {
        this.patchId = patchId;
        this.recordedAt = LocalDateTime.now();
    }
}
