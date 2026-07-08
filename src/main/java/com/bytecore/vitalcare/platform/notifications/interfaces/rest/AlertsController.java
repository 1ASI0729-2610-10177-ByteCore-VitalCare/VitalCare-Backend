package com.bytecore.vitalcare.platform.notifications.interfaces.rest;

import com.bytecore.vitalcare.platform.notifications.application.commandservices.AlertCommandService;
import com.bytecore.vitalcare.platform.notifications.application.queryservices.AlertQueryService;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.MarkAlertAsReadCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertByIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByUserIdQuery;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.AlertResource;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.CreateAlertResource;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertsController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertsController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @GetMapping
    public ResponseEntity<List<AlertResource>> getAlerts(@AuthenticationPrincipal UserDetailsImpl user) {
        var alerts = alertQueryService.handle(new GetAlertsByUserIdQuery(user.getId()))
                .stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<?> getAlertById(@PathVariable Long alertId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        var result = alertQueryService.handle(new GetAlertByIdQuery(alertId));

        if (result.isEmpty() || !result.get().getUserId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        var resource = AlertResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<?> createAlert(@RequestBody CreateAlertResource resource) {
        var command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = alertCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AlertResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{alertId}/read")
    public ResponseEntity<?> markAlertAsRead(@PathVariable Long alertId) {
        var command = new MarkAlertAsReadCommand(alertId);
        var result = alertCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AlertResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
