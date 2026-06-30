package com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {
    List<Location> findByPatchId(Long patchId);
}
