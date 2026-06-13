package com.bytecore.vitalcare.platform.notifications.interfaces.rest;

import com.bytecore.vitalcare.platform.notifications.application.commandservices.AlertCommandService;
import com.bytecore.vitalcare.platform.notifications.application.queryservices.AlertQueryService;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.MarkAlertAsReadCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAllAlertsQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertByIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByPatientIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByUserIdQuery;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.AlertResource;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.CreateAlertResource;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AlertResource>> getAlerts(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long patientId) {

        List<AlertResource> alerts;

        if (userId != null) {
            alerts = alertQueryService.handle(new GetAlertsByUserIdQuery(userId))
                    .stream()
                    .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else if (patientId != null) {
            alerts = alertQueryService.handle(new GetAlertsByPatientIdQuery(patientId))
                    .stream()
                    .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            alerts = alertQueryService.handle(new GetAllAlertsQuery())
                    .stream()
                    .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }

        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<?> getAlertById(@PathVariable Long alertId) {
        var query = new GetAlertByIdQuery(alertId);
        var result = alertQueryService.handle(query);

        if (result.isEmpty()) {
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
