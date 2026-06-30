package com.bytecore.vitalcare.platform.iam.application.commandservices;

import com.bytecore.vitalcare.platform.iam.domain.model.commands.UpdateUserPreferenceCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.UserPreference;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface UserPreferenceCommandService {
    Result<UserPreference, ApplicationError> handle(UpdateUserPreferenceCommand command);
}
