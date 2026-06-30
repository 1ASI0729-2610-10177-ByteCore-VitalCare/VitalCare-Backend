package com.bytecore.vitalcare.platform.patients.application.internal.queryservices;

import com.bytecore.vitalcare.platform.patients.application.queryservices.PatchQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchesByPatientIdQuery;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.PatchJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatchQueryServiceImpl implements PatchQueryService {

    private final PatchJpaRepository patchRepository;

    public PatchQueryServiceImpl(PatchJpaRepository patchRepository) {
        this.patchRepository = patchRepository;
    }

    @Override
    public Optional<Patch> handle(GetPatchByIdQuery query) {
        return patchRepository.findById(query.patchId());
    }

    @Override
    public List<Patch> handle(GetPatchesByPatientIdQuery query) {
        return patchRepository.findByPatientId(query.patientId());
    }
}
