package com.bytecore.vitalcare.platform.patients.application.internal.commandservices;

import com.bytecore.vitalcare.platform.patients.application.commandservices.VitalSignCommandService;
import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeleteVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.RecordVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdateVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.VitalSignJpaRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VitalSignCommandServiceImpl implements VitalSignCommandService {

    private final VitalSignJpaRepository vitalSignRepository;

    public VitalSignCommandServiceImpl(VitalSignJpaRepository vitalSignRepository) {
        this.vitalSignRepository = vitalSignRepository;
    }

    @Override
    @Transactional
    public Result<VitalSign, ApplicationError> handle(RecordVitalSignCommand command) {
        var vs = new VitalSign(command.patchId());
        vs.setGlucoseLevel(command.glucoseLevel());
        vs.setLactateConcentration(command.lactateConcentration());
        vs.setAlcoholLevel(command.alcoholLevel());
        vs.setKetones(command.ketones());
        vs.setBloodPressure(command.bloodPressure());
        vs.setTemperature(command.temperature());
        vs.setOxygenSaturation(command.oxygenSaturation());
        vs.setSodiumPotassium(command.sodiumPotassium());
        vs.setHeartRate(command.heartRate());
        vs.setCytokines(command.cytokines());
        vs.setTCells(command.tCells());
        vs.setHumidity(command.humidity());
        vs.setAtmosphericPressure(command.atmosphericPressure());
        vs.setAirQuality(command.airQuality());
        return Result.success(vitalSignRepository.save(vs));
    }

    @Override
    @Transactional
    public Result<VitalSign, ApplicationError> handle(UpdateVitalSignCommand command) {
        return vitalSignRepository.findById(command.id()).map(vs -> {
            vs.setPatchId(command.patchId());
            vs.setRecordedAt(command.recordedAt());
            vs.setGlucoseLevel(command.glucoseLevel());
            vs.setLactateConcentration(command.lactateConcentration());
            vs.setAlcoholLevel(command.alcoholLevel());
            vs.setKetones(command.ketones());
            vs.setBloodPressure(command.bloodPressure());
            vs.setTemperature(command.temperature());
            vs.setOxygenSaturation(command.oxygenSaturation());
            vs.setSodiumPotassium(command.sodiumPotassium());
            vs.setHeartRate(command.heartRate());
            vs.setCytokines(command.cytokines());
            vs.setTCells(command.tCells());
            vs.setHumidity(command.humidity());
            vs.setAtmosphericPressure(command.atmosphericPressure());
            vs.setAirQuality(command.airQuality());
            return Result.<VitalSign, ApplicationError>success(vitalSignRepository.save(vs));
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("VitalSign", command.id().toString())));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeleteVitalSignCommand command) {
        if (!vitalSignRepository.existsById(command.vitalSignId())) {
            return Result.failure(ApplicationError.notFound("VitalSign", command.vitalSignId().toString()));
        }
        vitalSignRepository.deleteById(command.vitalSignId());
        return Result.success(null);
    }
}
