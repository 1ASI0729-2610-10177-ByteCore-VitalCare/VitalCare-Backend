package com.bytecore.vitalcare.platform.patients.domain.model.aggregates;

import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.Gender;
import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
public class Patient extends AbstractDomainAggregateRoot<Patient> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    @Setter
    private Gender gender;
    @Setter
    private String photo;
    @Setter
    private Long userId;

    public Patient() {
    }

    public Patient(String name, LocalDate birthDate, Gender gender, String photo, Long userId) {
        this();
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.photo = photo;
        this.userId = userId;
    }
}
