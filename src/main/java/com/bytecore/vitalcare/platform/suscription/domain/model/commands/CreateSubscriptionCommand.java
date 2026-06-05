package com.bytecore.vitalcare.platform.suscription.domain.model.commands;

import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionPlan;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateSubscriptionCommand(
        SubscriptionPlan plan,
        BigDecimal price,
        LocalDate startDate,
        LocalDate endDate,
        Long userId
) {
}
