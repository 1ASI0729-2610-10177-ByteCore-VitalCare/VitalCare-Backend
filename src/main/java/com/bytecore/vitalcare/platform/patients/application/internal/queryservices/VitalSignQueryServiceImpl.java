package com.bytecore.vitalcare.platform.patients.application.internal.queryservices;

import com.bytecore.vitalcare.platform.patients.application.queryservices.VitalSignQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetVitalSignByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetVitalSignsByPatchIdQuery;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.VitalSignJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VitalSignQueryServiceImpl implements VitalSignQueryService {

    private final VitalSignJpaRepository vitalSignRepository;

    public VitalSignQueryServiceImpl(VitalSignJpaRepository vitalSignRepository) {
        this.vitalSignRepository = vitalSignRepository;
    }

    @Override
    public Optional<VitalSign> handle(GetVitalSignByIdQuery query) {
        return vitalSignRepository.findById(query.vitalSignId());
    }

    @Override
    public List<VitalSign> handle(GetVitalSignsByPatchIdQuery query) {
        return vitalSignRepository.findByPatchId(query.patchId());
    }
}
