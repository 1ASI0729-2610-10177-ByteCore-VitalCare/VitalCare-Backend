package com.bytecore.vitalcare.platform.patients.domain.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;

import java.util.List;
import java.util.Optional;

public interface VitalSignRepository {
    Optional<VitalSign> findById(Long id);
    List<VitalSign> findByPatchId(Long patchId);
    List<VitalSign> findAll();
    VitalSign save(VitalSign vitalSign);
    void deleteById(Long id);
}
