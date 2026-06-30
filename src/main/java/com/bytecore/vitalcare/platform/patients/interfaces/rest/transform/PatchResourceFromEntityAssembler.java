package com.bytecore.vitalcare.platform.patients.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.PatchResource;

public class PatchResourceFromEntityAssembler {

    public static PatchResource toResourceFromEntity(Patch patch) {
        return new PatchResource(
                patch.getId(),
                patch.getPatchCode(),
                patch.getLinkedAt() != null ? patch.getLinkedAt().toString() : null,
                patch.getStatus() != null ? patch.getStatus().name() : null,
                patch.getPatientId()
        );
    }
}
