package com.bytecore.vitalcare.platform.suscription.application.commandservices;

import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import com.bytecore.vitalcare.platform.suscription.domain.model.aggregates.Subscription;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.CreateSubscriptionCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.UpdateSubscriptionPlanCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.UpdateSubscriptionStatusCommand;

public interface SubscriptionCommandService {
    Result<Subscription, ApplicationError> handle(CreateSubscriptionCommand command);
    Result<Subscription, ApplicationError> handle(UpdateSubscriptionStatusCommand command);
    Result<Subscription, ApplicationError> handle(UpdateSubscriptionPlanCommand command);
}