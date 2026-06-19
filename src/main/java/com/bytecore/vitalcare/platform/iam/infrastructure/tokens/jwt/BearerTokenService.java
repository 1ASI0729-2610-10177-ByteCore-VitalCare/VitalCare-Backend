package com.bytecore.vitalcare.platform.iam.infrastructure.tokens.jwt;

import com.bytecore.vitalcare.platform.iam.application.internal.outboundservices.TokenService;
import com.bytecore.vitalcare.platform.iam.domain.model.aggregates.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BearerTokenService implements TokenService {

    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    private final String secret;
    private final long expirationDays;
    private final ObjectMapper objectMapper;

    public BearerTokenService(
            @Value("${authorization.jwt.secret}") String secret,
            @Value("${authorization.jwt.expiration.days}") long expirationDays,
            ObjectMapper objectMapper
    ) {
        this.secret = secret;
        this.expirationDays = expirationDays;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateToken(User user) {
        var now = Instant.now();
        var header = new LinkedHashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        var payload = new LinkedHashMap<String, Object>();
        payload.put("sub", user.getEmail());
        payload.put("id", user.getId());
        payload.put("name", user.getName());
        payload.put("iat", now.getEpochSecond());
        payload.put("exp", now.plus(this.expirationDays, ChronoUnit.DAYS).getEpochSecond());

        var unsignedToken = "%s.%s".formatted(toBase64UrlJson(header), toBase64UrlJson(payload));
        return "%s.%s".formatted(unsignedToken, sign(unsignedToken));
    }

    public Optional<String> extractSubject(String token) {
        return readVerifiedPayload(token)
                .filter(payload -> payload.containsKey("sub"))
                .filter(this::isNotExpired)
                .map(payload -> payload.get("sub").toString());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractSubject(token)
                .map(subject -> subject.equals(userDetails.getUsername()))
                .orElse(false);
    }

    private Optional<Map<String, Object>> readVerifiedPayload(String token) {
        try {
            var parts = token.split("\\.");
            if (parts.length != 3) return Optional.empty();

            var unsignedToken = "%s.%s".formatted(parts[0], parts[1]);
            if (!constantTimeEquals(sign(unsignedToken), parts[2])) return Optional.empty();

            var payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            return Optional.of(this.objectMapper.readValue(payloadJson, MAP_TYPE));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private boolean isNotExpired(Map<String, Object> payload) {
        if (!payload.containsKey("exp")) return false;
        var expiration = Long.parseLong(payload.get("exp").toString());
        return Instant.now().getEpochSecond() < expiration;
    }

    private String toBase64UrlJson(Map<String, Object> value) {
        try {
            var json = this.objectMapper.writeValueAsBytes(value);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(json);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to serialize JWT content", ex);
        }
    }

    private String sign(String value) {
        try {
            var mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(this.secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to sign JWT", ex);
        }
    }

    private boolean constantTimeEquals(String expected, String actual) {
        return MessageDigestUtil.constantTimeEquals(
                expected.getBytes(StandardCharsets.UTF_8),
                actual.getBytes(StandardCharsets.UTF_8));
    }
}
