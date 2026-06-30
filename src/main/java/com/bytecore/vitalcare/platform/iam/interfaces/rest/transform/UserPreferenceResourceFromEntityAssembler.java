package com.bytecore.vitalcare.platform.iam.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.UserPreference;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.UserPreferenceResource;

public class UserPreferenceResourceFromEntityAssembler {

    public static UserPreferenceResource toResourceFromEntity(UserPreference entity, Long userId) {
        return new UserPreferenceResource(
                userId,
                entity.getLanguage(),
                entity.getFontSize().name(),
                entity.getBackgroundColor().name(),
                entity.isEmailNotifications(),
                entity.isPushNotifications()
        );
    }
}
