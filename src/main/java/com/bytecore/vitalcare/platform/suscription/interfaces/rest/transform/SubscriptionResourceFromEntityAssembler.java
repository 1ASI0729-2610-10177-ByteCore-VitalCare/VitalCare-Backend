package com.bytecore.vitalcare.platform.suscription.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {

    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(
            entity.getId(),
            entity.getUserId(),
            entity.getPlan().name(),       
            entity.getPrice(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getStatus().name()     
        );
    }
}