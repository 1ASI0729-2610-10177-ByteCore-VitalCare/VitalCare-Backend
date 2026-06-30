package com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatchJpaRepository extends JpaRepository<Patch, Long> {
    List<Patch> findByPatientId(Long patientId);
    Optional<Patch> findByPatchCode(String patchCode);
}
