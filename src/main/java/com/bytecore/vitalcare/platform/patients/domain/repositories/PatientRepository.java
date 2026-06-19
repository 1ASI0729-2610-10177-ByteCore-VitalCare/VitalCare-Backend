package com.bytecore.vitalcare.platform.patients.domain.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> findById(Long id);
    List<Patient> findAll();
    List<Patient> findByUserId(Long userId);
    Patient save(Patient patient);
    boolean existsById(Long id);
    void deleteById(Long id);
}
