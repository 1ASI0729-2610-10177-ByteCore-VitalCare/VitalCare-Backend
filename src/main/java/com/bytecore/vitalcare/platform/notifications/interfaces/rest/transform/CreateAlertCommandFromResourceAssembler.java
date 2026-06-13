package com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.notifications.domain.model.commands.CreateAlertCommand;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.CreateAlertResource;

public class CreateAlertCommandFromResourceAssembler {

    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource) {
        return new CreateAlertCommand(resource.type(), resource.description(), resource.userId(), resource.patientId());
    }
}
