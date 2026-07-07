package com.bytecore.vitalcare.platform.iam.domain.model.entities;

import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.BackgroundColor;
import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.FontSize;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPreference {
    private String language;
    private FontSize fontSize;
    private BackgroundColor backgroundColor;
    private boolean emailNotifications;
    private boolean pushNotifications;

    public static UserPreference getDefault() {
        return new UserPreference("en", FontSize.MEDIUM, BackgroundColor.LIGHT, true, true);
    }
}
