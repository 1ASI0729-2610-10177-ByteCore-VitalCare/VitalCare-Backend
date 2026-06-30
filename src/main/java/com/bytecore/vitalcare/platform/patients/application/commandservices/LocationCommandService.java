package com.bytecore.vitalcare.platform.patients.application.commandservices;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeleteLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.RecordLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdateLocationCommand;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface LocationCommandService {
    Result<Location, ApplicationError> handle(RecordLocationCommand command);
    Result<Location, ApplicationError> handle(UpdateLocationCommand command);
    Result<Void, ApplicationError> handle(DeleteLocationCommand command);
}
