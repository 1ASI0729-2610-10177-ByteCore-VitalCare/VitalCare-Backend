package com.bytecore.vitalcare.platform.notifications.application.queryservices;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertByIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAllAlertsQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByPatientIdQuery;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetAlertsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {
    Optional<Alert> handle(GetAlertByIdQuery query);
    List<Alert> handle(GetAllAlertsQuery query);
    List<Alert> handle(GetAlertsByUserIdQuery query);
    List<Alert> handle(GetAlertsByPatientIdQuery query);
}
