package com.bytecore.vitalcare.platform.iam.application.internal.outboundservices;

public interface HashingService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
