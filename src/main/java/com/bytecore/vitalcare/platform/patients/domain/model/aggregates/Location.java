package com.bytecore.vitalcare.platform.patients.domain.model.aggregates;

import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Location extends AbstractDomainAggregateRoot<Location> {

    @Setter private Long id;
    @Setter private BigDecimal latitude;
    @Setter private BigDecimal longitude;
    @Setter private LocalDateTime recordedAt;
    @Setter private Long patchId;

    public Location() {}

    public Location(BigDecimal latitude, BigDecimal longitude, Long patchId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.patchId = patchId;
        this.recordedAt = LocalDateTime.now();
    }
}
