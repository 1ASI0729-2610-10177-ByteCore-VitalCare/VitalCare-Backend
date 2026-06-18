package com.bytecore.vitalcare.platform.suscription.application.queryservices;

import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetAllSubscriptionsQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface SubscriptionQueryService {
    List<Subscription> handle(GetAllSubscriptionsQuery query);
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);
    Optional<Subscription> handle(GetSubscriptionByUserIdQuery query);
}