package com.user.Dto.AuthDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Oauth2Info {

    private String userName;
    private String providerId;
    private String ProviderName;

}