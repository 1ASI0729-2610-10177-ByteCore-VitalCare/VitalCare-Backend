package com.bytecore.vitalcare.platform.patients.domain.model.entities;

import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.PatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "patches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patchCode;
    private LocalDateTime linkedAt;

    @Enumerated(EnumType.STRING)
    private PatchStatus status;

    private Long patientId;

    public Patch(String patchCode, Long patientId) {
        this.patchCode = patchCode;
        this.patientId = patientId;
        this.linkedAt = LocalDateTime.now();
        this.status = PatchStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.status == PatchStatus.ACTIVE;
    }
}
