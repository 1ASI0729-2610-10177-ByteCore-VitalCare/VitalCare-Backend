package com.bytecore.vitalcare.platform.patients.application.internal.commandservices;

import com.bytecore.vitalcare.platform.patients.application.commandservices.PatientCommandService;
import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.CreatePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatientCommand;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.PatientJpaRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {

    private final PatientJpaRepository patientRepository;

    public PatientCommandServiceImpl(PatientJpaRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public Result<Patient, ApplicationError> handle(CreatePatientCommand command) {
        var patient = new Patient(command.name(), command.birthDate(), command.gender(), command.photo(), command.userId());
        return Result.success(patientRepository.save(patient));
    }

    @Override
    @Transactional
    public Result<Patient, ApplicationError> handle(UpdatePatientCommand command) {
        return patientRepository.findById(command.id()).map(patient -> {
            patient.setName(command.name());
            patient.setBirthDate(command.birthDate());
            patient.setGender(command.gender());
            patient.setPhoto(command.photo());
            patient.setUserId(command.userId());
            return Result.<Patient, ApplicationError>success(patientRepository.save(patient));
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("Patient", command.id().toString())));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeletePatientCommand command) {
        if (!patientRepository.existsById(command.patientId())) {
            return Result.failure(ApplicationError.notFound("Patient", command.patientId().toString()));
        }
        patientRepository.deleteById(command.patientId());
        return Result.success(null);
    }
}
