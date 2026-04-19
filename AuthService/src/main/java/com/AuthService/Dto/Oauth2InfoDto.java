package com.AuthService.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Oauth2InfoDto {

    private String userName;
    private String providerId;
    private String ProviderName;

}
