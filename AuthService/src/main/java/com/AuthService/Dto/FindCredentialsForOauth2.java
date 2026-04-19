package com.AuthService.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindCredentialsForOauth2 {

    private String providerId;
    private String providerName;
    private String userName;

    public FindCredentialsForOauth2(String providerId, String providerName, String userName) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.userName = userName;
    }
}
