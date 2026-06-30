package com.bytecore.vitalcare.platform.patients.application.commandservices;

import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.LinkPatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface PatchCommandService {
    Result<Patch, ApplicationError> handle(LinkPatchCommand command);
    Result<Patch, ApplicationError> handle(UpdatePatchCommand command);
    Result<Void, ApplicationError> handle(DeletePatchCommand command);
}
