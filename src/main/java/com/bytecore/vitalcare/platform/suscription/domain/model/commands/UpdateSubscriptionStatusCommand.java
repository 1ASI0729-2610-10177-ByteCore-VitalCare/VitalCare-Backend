package com.bytecore.vitalcare.platform.suscription.domain.model.commands;

import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionStatus;

public record UpdateSubscriptionStatusCommand(Long subscriptionId, SubscriptionStatus status) {
}
