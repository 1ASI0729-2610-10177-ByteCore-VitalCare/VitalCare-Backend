package com.bytecore.vitalcare.platform.notifications.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter private Long id;
    @Setter private String nombre;
    @Setter private String fecha;
    @Setter private String descripcion;
    @Setter private Long patientId;
    @Setter private Long usersId;

    public Notification() {}

    public Notification(String nombre, String fecha, String descripcion, Long patientId, Long usersId) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.patientId = patientId;
        this.usersId = usersId;
    }
}
