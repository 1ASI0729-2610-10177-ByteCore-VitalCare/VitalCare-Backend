package com.bytecore.vitalcare.platform.iam.application.internal.commandservices;

import com.bytecore.vitalcare.platform.iam.application.commandservices.UserPreferenceCommandService;
import com.bytecore.vitalcare.platform.iam.domain.model.commands.UpdateUserPreferenceCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.UserPreference;
import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPreferenceCommandServiceImpl implements UserPreferenceCommandService {

    private final UserRepository userRepository;

    public UserPreferenceCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Result<UserPreference, ApplicationError> handle(UpdateUserPreferenceCommand command) {
        return userRepository.findById(command.userId()).map(user -> {
            var preference = new UserPreference(
                    command.language(),
                    command.fontSize(),
                    command.backgroundColor(),
                    command.emailNotifications(),
                    command.pushNotifications()
            );
            user.setPreference(preference);
            userRepository.save(user);
            return Result.<UserPreference, ApplicationError>success(preference);
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("User", command.userId().toString())));
    }
}
