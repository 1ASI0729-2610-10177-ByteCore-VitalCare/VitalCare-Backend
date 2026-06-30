package com.bytecore.vitalcare.platform.iam.application.internal.queryservices;

import com.bytecore.vitalcare.platform.iam.application.queryservices.UserPreferenceQueryService;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.UserPreference;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetUserPreferencesByUserIdQuery;
import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPreferenceQueryServiceImpl implements UserPreferenceQueryService {

    private final UserRepository userRepository;

    public UserPreferenceQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserPreference> handle(GetUserPreferencesByUserIdQuery query) {
        return userRepository.findById(query.userId()).map(user -> user.getPreference());
    }
}
