package com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateSubscriptionResource(
    @NotNull Long userId,
    @NotNull String plan,       
    @NotNull Double price,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate
) {}