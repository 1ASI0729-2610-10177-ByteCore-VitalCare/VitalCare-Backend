package com.bytecore.vitalcare.platform.patients.application.internal.commandservices;

import com.bytecore.vitalcare.platform.patients.application.commandservices.PatchCommandService;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.LinkPatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.PatchJpaRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatchCommandServiceImpl implements PatchCommandService {

    private final PatchJpaRepository patchRepository;

    public PatchCommandServiceImpl(PatchJpaRepository patchRepository) {
        this.patchRepository = patchRepository;
    }

    @Override
    @Transactional
    public Result<Patch, ApplicationError> handle(LinkPatchCommand command) {
        var patch = new Patch(command.patchCode(), command.patientId());
        return Result.success(patchRepository.save(patch));
    }

    @Override
    @Transactional
    public Result<Patch, ApplicationError> handle(UpdatePatchCommand command) {
        return patchRepository.findById(command.id()).map(patch -> {
            patch.setPatchCode(command.patchCode());
            patch.setLinkedAt(command.linkedAt());
            patch.setStatus(command.status());
            patch.setPatientId(command.patientId());
            return Result.<Patch, ApplicationError>success(patchRepository.save(patch));
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("Patch", command.id().toString())));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeletePatchCommand command) {
        if (!patchRepository.existsById(command.patchId())) {
            return Result.failure(ApplicationError.notFound("Patch", command.patchId().toString()));
        }
        patchRepository.deleteById(command.patchId());
        return Result.success(null);
    }
}
