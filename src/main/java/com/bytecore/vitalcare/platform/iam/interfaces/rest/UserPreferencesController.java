package com.bytecore.vitalcare.platform.iam.interfaces.rest;

import com.bytecore.vitalcare.platform.iam.application.commandservices.UserPreferenceCommandService;
import com.bytecore.vitalcare.platform.iam.application.queryservices.UserPreferenceQueryService;
import com.bytecore.vitalcare.platform.iam.domain.model.commands.UpdateUserPreferenceCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetUserPreferencesByUserIdQuery;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.CreateUserPreferenceResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.UserPreferenceResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.transform.UpdateUserPreferenceCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.transform.UserPreferenceResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user_preferences", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Preferences", description = "User preferences management endpoints")
public class UserPreferencesController {

    private final UserPreferenceCommandService commandService;
    private final UserPreferenceQueryService queryService;

    public UserPreferencesController(UserPreferenceCommandService commandService,
                                     UserPreferenceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<UserPreferenceResource> getByUserId(@AuthenticationPrincipal UserDetailsImpl user) {
        var userId = user.getId();
        var query = new GetUserPreferencesByUserIdQuery(userId);
        var preference = queryService.handle(query);
        return preference
                .map(p -> ResponseEntity.ok(UserPreferenceResourceFromEntityAssembler.toResourceFromEntity(p, userId)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody CreateUserPreferenceResource resource,
                                            @AuthenticationPrincipal UserDetailsImpl user) {
        var userId = user.getId();
        // The preference always belongs to the authenticated user, never to a body-supplied id.
        var base = UpdateUserPreferenceCommandFromResourceAssembler.toCommandFromResource(resource);
        var command = new UpdateUserPreferenceCommand(userId, base.language(), base.fontSize(),
                base.backgroundColor(), base.emailNotifications(), base.pushNotifications());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                p -> UserPreferenceResourceFromEntityAssembler.toResourceFromEntity(p, userId),
                HttpStatus.CREATED
        );
    }
}
