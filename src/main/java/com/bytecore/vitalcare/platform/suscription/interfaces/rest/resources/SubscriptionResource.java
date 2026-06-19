package com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionResource(
    Long id,
    Long userId,      
    String plan,     
    BigDecimal price,
    LocalDate startDate,
    LocalDate endDate,
    String status     
) {}