package com.bytecore.vitalcare.platform.suscription.application.internal.commandservices;

import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import com.bytecore.vitalcare.platform.suscription.application.commandservices.SubscriptionCommandService;
import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.CreateSubscriptionCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.UpdateSubscriptionStatusCommand;
import com.bytecore.vitalcare.platform.suscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Result<Subscription, ApplicationError> handle(CreateSubscriptionCommand command) {
        try {
            var subscription = new Subscription(command);
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("SubscriptionCreation", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(UpdateSubscriptionStatusCommand command) {
        return subscriptionRepository.findById(command.subscriptionId()).map(subscription -> {
            subscription.setStatus(command.status());
            var updated = subscriptionRepository.save(subscription);
            return Result.<Subscription, ApplicationError>success(updated);
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("Subscription", command.subscriptionId().toString())));
    }
}