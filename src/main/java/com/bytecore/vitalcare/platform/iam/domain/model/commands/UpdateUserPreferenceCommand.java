package com.bytecore.vitalcare.platform.iam.domain.model.commands;

import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.BackgroundColor;
import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.FontSize;

public record UpdateUserPreferenceCommand(
        Long userId,
        String language,
        FontSize fontSize,
        BackgroundColor backgroundColor,
        boolean emailNotifications,
        boolean pushNotifications
) {
}
