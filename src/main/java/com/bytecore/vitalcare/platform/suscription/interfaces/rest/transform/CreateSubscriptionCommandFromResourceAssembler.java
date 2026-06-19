package com.bytecore.vitalcare.platform.suscription.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.suscription.domain.model.commands.CreateSubscriptionCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionPlan;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources.CreateSubscriptionResource;

import java.math.BigDecimal;

public class CreateSubscriptionCommandFromResourceAssembler {

    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        var planEnum = SubscriptionPlan.valueOf(resource.plan().toUpperCase());

        return new CreateSubscriptionCommand(
            planEnum,
            BigDecimal.valueOf(resource.price()),
            resource.startDate(),
            resource.endDate(),
            resource.userId()
        );
    }
}