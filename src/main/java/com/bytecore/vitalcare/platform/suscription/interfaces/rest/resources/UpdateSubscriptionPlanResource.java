package com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources;

import java.math.BigDecimal;

public record UpdateSubscriptionPlanResource(
        String plan,
        BigDecimal price
) {}
