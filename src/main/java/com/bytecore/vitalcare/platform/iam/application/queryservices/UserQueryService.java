package com.bytecore.vitalcare.platform.iam.application.queryservices;

import com.bytecore.vitalcare.platform.iam.domain.model.aggregates.User;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetUserByIdQuery;

import java.util.Optional;

public interface UserQueryService {
    Optional<User> handle(GetUserByIdQuery query);
}
