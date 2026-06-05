package com.bytecore.vitalcare.platform.patients.domain.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;

import java.util.List;
import java.util.Optional;

public interface PatchRepository {
    Optional<Patch> findById(Long id);
    Optional<Patch> findByPatchCode(String patchCode);
    List<Patch> findByPatientId(Long patientId);
    Patch save(Patch patch);
    boolean existsByPatchCode(String patchCode);
}
