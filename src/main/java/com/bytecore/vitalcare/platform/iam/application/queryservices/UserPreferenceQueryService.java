package com.bytecore.vitalcare.platform.iam.application.queryservices;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.UserPreference;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetUserPreferencesByUserIdQuery;

import java.util.Optional;

public interface UserPreferenceQueryService {
    Optional<UserPreference> handle(GetUserPreferencesByUserIdQuery query);
}
