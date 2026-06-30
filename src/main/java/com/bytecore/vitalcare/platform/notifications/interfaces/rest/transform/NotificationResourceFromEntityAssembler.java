package com.bytecore.vitalcare.platform.notifications.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Notification;
import com.bytecore.vitalcare.platform.notifications.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {

    public static NotificationResource toResourceFromEntity(Notification notification) {
        return new NotificationResource(
                notification.getId(),
                notification.getNombre(),
                notification.getFecha(),
                notification.getDescripcion(),
                notification.getPatientId(),
                notification.getUsersId()
        );
    }
}
