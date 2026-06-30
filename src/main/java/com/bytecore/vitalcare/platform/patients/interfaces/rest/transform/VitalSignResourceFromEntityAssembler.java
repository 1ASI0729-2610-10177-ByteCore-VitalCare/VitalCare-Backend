package com.bytecore.vitalcare.platform.patients.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.VitalSignResource;

public class VitalSignResourceFromEntityAssembler {

    public static VitalSignResource toResourceFromEntity(VitalSign vs) {
        return new VitalSignResource(
                vs.getId(),
                vs.getRecordedAt() != null ? vs.getRecordedAt().toString() : null,
                vs.getGlucoseLevel(),
                vs.getLactateConcentration(),
                vs.getAlcoholLevel(),
                vs.getKetones(),
                vs.getBloodPressure(),
                vs.getTemperature(),
                vs.getOxygenSaturation(),
                vs.getSodiumPotassium(),
                vs.getHeartRate(),
                vs.getCytokines(),
                vs.getTCells(),
                vs.getHumidity(),
                vs.getAtmosphericPressure(),
                vs.getAirQuality(),
                vs.getPatchId()
        );
    }
}
