package com.bytecore.vitalcare.platform.iam.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.iam.domain.model.aggregates.User;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.UserResource;

import java.time.Instant;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getName(),
                user.getEmail(),
                Instant.now().toString()
        );
    }
}
