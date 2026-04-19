package com.AuthService.FeignClient.FeignClientUtilities;
import org.springframework.stereotype.Component;



@Component
public class ServiceTokenProvider {

    private final String secret = "hfgtsdart6sgsjdhgyutadndmdmdlsksi8esjsnxhdgwteryndjkjsl";

    public String getServiceToken() {

        return JwtBuilderUtil.createServiceToken("oauth-service", secret);
    }
}
