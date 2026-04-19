package com.AuthService.Security.SecurityUtils;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class ProviderId {

    public String getProviderId(String providerName, OAuth2User user){

                   return  switch (providerName){
                        case "GOOGLE"-> user.getAttribute("sub");
                       default -> throw new RuntimeException("Please enter the correct details");
                        };
                    }
    }

