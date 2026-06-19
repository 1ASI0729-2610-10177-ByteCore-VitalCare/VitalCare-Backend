package com.bytecore.vitalcare.platform.suscription.application.internal.queryservices;

import com.bytecore.vitalcare.platform.suscription.application.queryservices.SubscriptionQueryService;
import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetAllSubscriptionsQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionByUserIdQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionStatus;
import com.bytecore.vitalcare.platform.suscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> handle(GetAllSubscriptionsQuery query) {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return subscriptionRepository.findById(query.subscriptionId());
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByUserIdQuery query) {
        return subscriptionRepository.findFirstByUserIdAndStatus(query.userId(), SubscriptionStatus.ACTIVE);
    }
}
