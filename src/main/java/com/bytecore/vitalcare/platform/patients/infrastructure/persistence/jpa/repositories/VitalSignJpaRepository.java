package com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VitalSignJpaRepository extends JpaRepository<VitalSign, Long> {
    List<VitalSign> findByPatchId(Long patchId);
}
