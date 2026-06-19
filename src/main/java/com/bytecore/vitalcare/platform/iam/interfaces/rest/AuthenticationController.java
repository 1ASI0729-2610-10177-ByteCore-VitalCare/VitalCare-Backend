package com.bytecore.vitalcare.platform.iam.interfaces.rest;

import com.bytecore.vitalcare.platform.iam.application.internal.commandservices.AuthenticationCommandService;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.SignInResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.SignUpResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationCommandService authenticationCommandService;

    public AuthenticationController(AuthenticationCommandService authenticationCommandService) {
        this.authenticationCommandService = authenticationCommandService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpResource resource) {
        var result = this.authenticationCommandService.signUp(resource.name(), resource.email(), resource.password());
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuthenticatedUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource resource) {
        var result = this.authenticationCommandService.signIn(resource.email(), resource.password());
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuthenticatedUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
