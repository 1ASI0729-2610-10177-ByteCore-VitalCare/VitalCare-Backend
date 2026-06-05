package com.bytecore.vitalcare.platform.patients.domain.model.entities;

import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.PatchStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Patch {
    private Long id;
    private String patchCode;
    private LocalDateTime linkedAt;
    private PatchStatus status;

    public Patch(String patchCode) {
        this.patchCode = patchCode;
        this.linkedAt = LocalDateTime.now();
        this.status = PatchStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.status == PatchStatus.ACTIVE;
    }
}
