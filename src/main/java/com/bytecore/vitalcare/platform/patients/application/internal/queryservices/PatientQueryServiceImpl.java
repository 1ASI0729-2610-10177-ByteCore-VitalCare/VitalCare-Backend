package com.bytecore.vitalcare.platform.patients.application.internal.queryservices;

import com.bytecore.vitalcare.platform.patients.application.queryservices.PatientQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientsByUserIdQuery;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.PatientJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientQueryServiceImpl implements PatientQueryService {

    private final PatientJpaRepository patientRepository;

    public PatientQueryServiceImpl(PatientJpaRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> handle(GetPatientByIdQuery query) {
        return patientRepository.findById(query.patientId());
    }

    @Override
    public List<Patient> handle(GetPatientsByUserIdQuery query) {
        return patientRepository.findByUserId(query.userId());
    }
}
