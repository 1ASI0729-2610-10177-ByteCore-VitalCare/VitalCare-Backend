package com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientJpaRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByUserId(Long userId);
}
