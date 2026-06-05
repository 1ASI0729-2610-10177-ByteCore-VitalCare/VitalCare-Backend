package com.bytecore.vitalcare.platform.patients.domain.model.aggregates;

import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.Gender;
import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Patient extends AbstractDomainAggregateRoot<Patient> {

    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private LocalDate birthDate;
    @Setter
    private Gender gender;
    @Setter
    private String photo;
    @Setter
    private Long userId;
    private List<Patch> patches;

    public Patient() {
        this.patches = new ArrayList<>();
    }

    public Patient(String name, LocalDate birthDate, Gender gender, String photo, Long userId) {
        this();
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.photo = photo;
        this.userId = userId;
    }

    public Patient linkPatch(Patch patch) {
        this.patches.add(patch);
        return this;
    }
}
