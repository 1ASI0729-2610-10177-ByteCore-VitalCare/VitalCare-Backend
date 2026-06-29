package com.bytecore.vitalcare.platform.suscription.interfaces.rest;

import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bytecore.vitalcare.platform.suscription.application.commandservices.SubscriptionCommandService;
import com.bytecore.vitalcare.platform.suscription.application.queryservices.SubscriptionQueryService;
import com.bytecore.vitalcare.platform.suscription.domain.model.queries.GetAllSubscriptionsQuery;
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

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscription management endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService) {
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
    @Operation(summary = "Get all subscriptions")
    public ResponseEntity<List<SubscriptionResource>> getAllSubscriptions() {
        var query = new GetAllSubscriptionsQuery();
        var subscriptions = subscriptionQueryService.handle(query);
        var resources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

}