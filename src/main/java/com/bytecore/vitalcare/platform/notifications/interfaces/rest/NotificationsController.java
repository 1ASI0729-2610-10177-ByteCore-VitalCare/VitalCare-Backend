package com.bytecore.vitalcare.platform.notifications.interfaces.rest;

import com.bytecore.vitalcare.platform.notifications.application.commandservices.NotificationCommandService;
import com.bytecore.vitalcare.platform.notifications.application.queryservices.NotificationQueryService;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.DeleteNotificationCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetNotificationsByUserIdQuery;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.CreateNotificationResource;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.NotificationResource;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification management endpoints")
public class NotificationsController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationsController(NotificationCommandService commandService,
                                   NotificationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResource>> getByUserId(@AuthenticationPrincipal UserDetailsImpl user) {
        var notifications = queryService.handle(new GetNotificationsByUserIdQuery(user.getId())).stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateNotificationResource resource,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        var command = new CreateNotificationCommand(
                resource.nombre(), resource.fecha(), resource.descripcion(),
                resource.patientId(), user.getId()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, NotificationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        // No get-by-id query exists, so confirm ownership via the user's own notifications.
        var owns = queryService.handle(new GetNotificationsByUserIdQuery(user.getId())).stream()
                .anyMatch(n -> n.getId().equals(id));
        if (!owns) {
            return ResponseEntity.notFound().build();
        }
        var result = commandService.handle(new DeleteNotificationCommand(id));
        return result instanceof com.bytecore.vitalcare.platform.shared.application.result.Result.Success<?, ?>
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
