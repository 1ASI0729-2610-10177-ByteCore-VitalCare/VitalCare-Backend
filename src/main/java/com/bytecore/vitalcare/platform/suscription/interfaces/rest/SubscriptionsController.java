package com.bytecore.vitalcare.platform.suscription.interfaces.rest;

import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bytecore.vitalcare.platform.suscription.application.commandservices.SubscriptionCommandService;
import com.bytecore.vitalcare.platform.suscription.application.queryservices.SubscriptionQueryService;
import com.bytecore.vitalcare.platform.suscription.domain.model.commands.UpdateSubscriptionPlanCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetAllSubscriptionsQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetSubscriptionsByUserIdQuery;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionPlan;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources.CreateSubscriptionResource;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.resources.SubscriptionResource;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.suscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createSubscription(@Valid @RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get subscriptions, optionally filtered by user")
    public ResponseEntity<List<SubscriptionResource>> getSubscriptions(
            @RequestParam(required = false) Long users_id) {
        List<SubscriptionResource> resources;
        if (users_id != null) {
            resources = subscriptionQueryService.handle(new GetSubscriptionsByUserIdQuery(users_id))
                    .stream()
                    .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            resources = subscriptionQueryService.handle(new GetAllSubscriptionsQuery())
                    .stream()
                    .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }
        return ResponseEntity.ok(resources);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update subscription plan and price")
    public ResponseEntity<?> updateSubscriptionPlan(@PathVariable Long id,
                                                    @RequestBody Map<String, Object> body) {
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
