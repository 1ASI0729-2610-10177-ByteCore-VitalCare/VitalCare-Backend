package com.bytecore.vitalcare.platform.iam.interfaces.rest;

import com.bytecore.vitalcare.platform.iam.application.queryservices.UserQueryService;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.UserResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User management endpoints")
public class UsersController {

    private final UserQueryService userQueryService;

    public UsersController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId,
                                                    @AuthenticationPrincipal UserDetailsImpl user) {
        // A user can only read their own record.
        if (!userId.equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var query = new GetUserByIdQuery(userId);
        return userQueryService.handle(query)
                .map(found -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
