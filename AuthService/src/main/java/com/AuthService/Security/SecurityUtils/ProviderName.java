package com.AuthService.Security.SecurityUtils;


import org.springframework.stereotype.Component;
@Component
public class ProviderName {

    public String getProviderName(String providerId) {
        if (providerId == null) {
            throw new IllegalArgumentException("providerId cannot be null");
        }

        return switch (providerId.toLowerCase()) {
            case "google" -> "GOOGLE";
            case "facebook" -> "FACEBOOK";
            default -> throw new IllegalArgumentException("Invalid providerId: " + providerId);
        };
    }
}
