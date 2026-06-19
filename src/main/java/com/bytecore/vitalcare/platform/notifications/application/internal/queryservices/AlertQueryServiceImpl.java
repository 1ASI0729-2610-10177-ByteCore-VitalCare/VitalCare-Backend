package com.bytecore.vitalcare.platform.notifications.application.internal.queryservices;

import com.bytecore.vitalcare.platform.notifications.application.queryservices.AlertQueryService;
import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertByIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAllAlertsQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByPatientIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByUserIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.repositories.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.alertId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public List<Alert> handle(GetAlertsByUserIdQuery query) {
        return alertRepository.findByUserId(query.userId());
    }

    @Override
    public List<Alert> handle(GetAlertsByPatientIdQuery query) {
        return alertRepository.findByPatientId(query.patientId());
    }
}
