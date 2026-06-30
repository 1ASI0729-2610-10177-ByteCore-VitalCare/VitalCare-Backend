package com.bytecore.vitalcare.platform.iam.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.iam.application.internal.commandservices.AuthenticationCommandService.AuthenticatedUser;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

public final class AuthenticatedUserResourceFromEntityAssembler {

    private AuthenticatedUserResourceFromEntityAssembler() {}

    public static AuthenticatedUserResource toResourceFromEntity(AuthenticatedUser authenticatedUser) {
        return new AuthenticatedUserResource(
                authenticatedUser.id(),
                authenticatedUser.name(),
                authenticatedUser.email(),
                authenticatedUser.token(),
                authenticatedUser.createdAt());
    }
}
