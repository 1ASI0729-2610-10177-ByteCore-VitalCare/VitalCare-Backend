package com.bytecore.vitalcare.platform.suscription.domain.model.commands;

import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionPlan;

import java.math.BigDecimal;

public record UpdateSubscriptionPlanCommand(Long subscriptionId, SubscriptionPlan plan, BigDecimal price) {}
