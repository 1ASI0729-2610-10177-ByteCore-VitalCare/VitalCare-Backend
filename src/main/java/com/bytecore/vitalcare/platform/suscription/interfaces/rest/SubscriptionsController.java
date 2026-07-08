package com.bytecore.vitalcare.platform.suscription.interfaces.rest;

import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bytecore.vitalcare.platform.suscription.application.commandservices.SubscriptionCommandService;
import com.bytecore.vitalcare.platform.suscription.application.queryservices.SubscriptionQueryService;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.CreateSubscriptionCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.UpdateSubscriptionPlanCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionsByUserIdQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionPlan;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources.CreateSubscriptionResource;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources.SubscriptionResource;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new subscription")
    public ResponseEntity<?> createSubscription(@Valid @RequestBody CreateSubscriptionResource resource,
                                                @AuthenticationPrincipal UserDetailsImpl user) {
        // The subscription always belongs to the authenticated user, never to a body-supplied id.
        var base = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var command = new CreateSubscriptionCommand(base.plan(), base.price(), base.startDate(),
                base.endDate(), user.getId());
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get the authenticated user's subscriptions")
    public ResponseEntity<List<SubscriptionResource>> getSubscriptions(
            @AuthenticationPrincipal UserDetailsImpl user) {
        var resources = subscriptionQueryService.handle(new GetSubscriptionsByUserIdQuery(user.getId()))
                .stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update subscription plan and price")
    public ResponseEntity<?> updateSubscriptionPlan(@PathVariable Long id,
                                                    @RequestBody Map<String, Object> body,
                                                    @AuthenticationPrincipal UserDetailsImpl user) {
        var owns = subscriptionQueryService.handle(new GetSubscriptionByIdQuery(id))
                .filter(s -> s.getUserId().equals(user.getId()))
                .isPresent();
        if (!owns) {
            return ResponseEntity.notFound().build();
        }
        var plan = SubscriptionPlan.valueOf(body.get("plan").toString().toUpperCase());
        var price = new BigDecimal(body.get("price").toString());
        var command = new UpdateSubscriptionPlanCommand(id, plan, price);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
