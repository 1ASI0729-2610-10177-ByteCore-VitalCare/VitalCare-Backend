package com.bytecore.vitalcare.platform.patients.application.commandservices;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.CreatePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatientCommand;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface PatientCommandService {
    Result<Patient, ApplicationError> handle(CreatePatientCommand command);
    Result<Patient, ApplicationError> handle(UpdatePatientCommand command);
    Result<Void, ApplicationError> handle(DeletePatientCommand command);
}
