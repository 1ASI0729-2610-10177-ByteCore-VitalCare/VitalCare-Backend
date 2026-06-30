package com.bytecore.vitalcare.platform.patients.application.commandservices;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeleteVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.RecordVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdateVitalSignCommand;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface VitalSignCommandService {
    Result<VitalSign, ApplicationError> handle(RecordVitalSignCommand command);
    Result<VitalSign, ApplicationError> handle(UpdateVitalSignCommand command);
    Result<Void, ApplicationError> handle(DeleteVitalSignCommand command);
}
