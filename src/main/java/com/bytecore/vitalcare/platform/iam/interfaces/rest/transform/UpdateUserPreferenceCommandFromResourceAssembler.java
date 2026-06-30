package com.bytecore.vitalcare.platform.iam.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.iam.domain.model.commands.UpdateUserPreferenceCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.BackgroundColor;
import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.FontSize;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.CreateUserPreferenceResource;

public class UpdateUserPreferenceCommandFromResourceAssembler {

    public static UpdateUserPreferenceCommand toCommandFromResource(CreateUserPreferenceResource resource) {
        return new UpdateUserPreferenceCommand(
                resource.users_id(),
                resource.language(),
                FontSize.valueOf(resource.fontSize().toUpperCase()),
                BackgroundColor.valueOf(resource.backgroundColor().toUpperCase()),
                resource.emailNotifications(),
                resource.pushNotifications()
        );
    }
}
