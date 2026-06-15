package com.bytecore.vitalcare.platform.iam.application.internal.outboundservices;

import com.bytecore.vitalcare.platform.iam.domain.model.aggregates.User;

public interface TokenService {
    String generateToken(User user);
}
