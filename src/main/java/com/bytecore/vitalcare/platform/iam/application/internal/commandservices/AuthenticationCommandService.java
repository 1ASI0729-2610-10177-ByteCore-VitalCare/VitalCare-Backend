package com.bytecore.vitalcare.platform.iam.application.internal.commandservices;

import com.bytecore.vitalcare.platform.iam.application.internal.outboundservices.HashingService;
import com.bytecore.vitalcare.platform.iam.application.internal.outboundservices.TokenService;
import com.bytecore.vitalcare.platform.iam.domain.model.aggregates.User;
import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthenticationCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public AuthenticationCommandService(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Transactional
    public Result<AuthenticatedUser, ApplicationError> signUp(String name, String email, String password) {
        if (this.userRepository.existsByEmail(email)) {
            return Result.failure(ApplicationError.conflict("user", "Email already exists"));
        }

        var encodedPassword = this.hashingService.encode(password);
        var user = this.userRepository.save(new User(name, email, encodedPassword));
        var token = this.tokenService.generateToken(user);
        return Result.success(AuthenticatedUser.fromUserAndToken(user, token, Instant.now().toString()));
    }

    @Transactional(readOnly = true)
    public Result<AuthenticatedUser, ApplicationError> signIn(String email, String password) {
        var user = this.userRepository.findByEmail(email);
        if (user.isEmpty() || !this.hashingService.matches(password, user.get().getPassword())) {
            return Result.failure(new ApplicationError(
                    "INVALID_CREDENTIALS",
                    "Invalid credentials",
                    "Email or password is incorrect"));
        }

        var token = this.tokenService.generateToken(user.get());
        return Result.success(AuthenticatedUser.fromUserAndToken(user.get(), token, Instant.now().toString()));
    }

    public record AuthenticatedUser(
            Long id,
            String name,
            String email,
            String token,
            String createdAt
    ) {
        static AuthenticatedUser fromUserAndToken(User user, String token, String createdAt) {
            return new AuthenticatedUser(user.getId(), user.getName(), user.getEmail(), token, createdAt);
        }
    }
}
